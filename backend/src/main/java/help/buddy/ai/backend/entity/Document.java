package help.buddy.ai.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "Document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCUMENT_ID")
    private Long documentId;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "UPLOADED_AT")
    private Date uploaded_at;
    
    

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploaded_at() {
        return uploaded_at;
    }

    public void setUploaded_at(Date uploaded_at) {
        this.uploaded_at = uploaded_at;
    }
}
