package pl.bluemedia.restdemo.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "resources")
public class Resource {
    
    @Id
    String id;
    String resName;
    String resPath;
    
    public Resource() {
    }

    public Resource(String resName, String resPath) {
        this.resName = resName;
        this.resPath = resPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }
    
}
