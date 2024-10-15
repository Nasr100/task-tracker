package taskTracker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Task {
	int id = 0;
	String status;
	String description ;
	String createdAt = null;
	String updatedAt = null;
	
	Task(String desc){
		id=id+1;
		this.description = desc;
		this.status = "todo";
		LocalDateTime dateTime = LocalDateTime.now(); //get the time now
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //format it
		this.createdAt = dateTime.format(myFormatObj); //assign it to the attribute
		this.updatedAt = "No updates";
	}
	
	public void setid(int id) {
		this.id=id;
	}
	public void setUpdatedat(String up) {
		this.createdAt = up;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}

	public int getId() {
		return this.id;
	}
}
