package help.buddy.ai.backend.services.implementations;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import help.buddy.ai.backend.entity.User;
import help.buddy.ai.backend.repository.DocumentRepository;
import help.buddy.ai.backend.services.DocumentParserService;
import help.buddy.ai.backend.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentParserService parserService;

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    private final String UPLOAD_DIR = "uploads/";

    @Override
    public void storeFile(MultipartFile file, User user) {
        try {
            // 1. Save Physical File
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) directory.mkdirs();
            String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // 2. Extract Text (Day 4 Task)
            String content = parserService.parseDocument(file);

            // 3. Save to SQL Database (Metadata)
            help.buddy.ai.backend.entity.Document docEntity = new help.buddy.ai.backend.entity.Document();
            docEntity.setFileName(file.getOriginalFilename());
            docEntity.setFilePath(filePath);
            docEntity.setUploaded_at(new Date());
            // docEntity.setUser(user); // Uncomment once you link User to Document in Entity
            help.buddy.ai.backend.entity.Document savedDoc = documentRepository.save(docEntity);

            // 4. Ingest into Vector DB (Day 6 Task)
            ingestIntoVectorDb(content, savedDoc.getId(), user.getId());

        } catch (IOException e) {
            throw new RuntimeException("Error storing file", e);
        }
    }

    private void ingestIntoVectorDb(String content, Long docId, Long userId) {
        // Create Metadata so we know WHICH user/document this text belongs to
        Metadata metadata = new Metadata();
        metadata.add("document_id", String.valueOf(docId));
        metadata.add("user_id", String.valueOf(userId));

        Document langchainDoc = Document.from(content, metadata);

        // The Ingestor handles Chunking + Embedding + Saving
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 50)) // Split into 500 token chunks
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(langchainDoc);
        System.out.println("Document " + docId + " ingested into Vector DB.");
    }
}
