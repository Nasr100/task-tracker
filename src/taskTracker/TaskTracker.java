package taskTracker;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class TaskTracker {
	private static ArrayList<Task> tasks = new ArrayList<Task>(); ;  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File tasksFile = createFile(); 
//		Task t1 = new Task("Buy groceries");
//		addTask(tasksFile,t1);
		
//		Task t2 = new Task("yyy");
//		addTask(tasksFile,t2);
		updateTask(tasksFile,1,"coding");
//		listallTasks(tasksFile);
		
	}
	
	
	
	static public File createFile() {
		File f = new File("Tasks.json");
		try {
			if (f.createNewFile()) {
				
		        System.out.println("File created: " + f.getName());
		        
		      } else {
		        System.out.println("File already exists.");
		      }
			
		}catch (IOException e) {
			System.out.println("error file note created");
			 e.printStackTrace();
		}
		return f;
	}
	
	
	static public void addTask(File f,Task t) {
		Scanner myScanner = null;
		try {
			myScanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String data = "";
		FileWriter myWriter;
		Gson gson = new Gson();
		while(myScanner.hasNextLine()) {
			data += myScanner.nextLine();
		}
		myScanner.close();
		if(data == "") {
			try {
				tasks.add(t);
				myWriter = new FileWriter(f);
				myWriter.write("[");
				gson.toJson(t,myWriter);
				myWriter.write("]");
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			Type listType = new TypeToken<Collection<Task>>(){}.getType();
			tasks = gson.fromJson(data,listType);
			t.setid((tasks.getLast().id) + 1);
			tasks.add(t);
			try {
				myWriter = new FileWriter(f);
				gson.toJson(tasks,myWriter);
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		}

	}
	
	static public<T> void WriteFile(File f,T data){
		FileWriter myWriter;
		Gson gson = new Gson();
		try {
			myWriter = new FileWriter(f);
			gson.toJson(data,myWriter);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	static public ArrayList<Task> tasksList(File f) {
		String data = "";
		Gson gson = new Gson();
		Scanner myScanner = null;
		try {
			myScanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(myScanner.hasNextLine()) {
			data += myScanner.nextLine();
		}
		myScanner.close();
		Type listType = new TypeToken<Collection<Task>>(){}.getType();
		tasks = gson.fromJson(data,listType);
		return tasks;
	}
	
	static public void listallTasks(File f ) {
		tasks = tasksList(f);
		for(Task T: tasks) {
			System.out.println("id: "+T.id);
			System.out.println("description: "+T.description);
			System.out.println("status: "+T.status);
			System.out.println("createdAt: "+T.createdAt);
			System.out.println("updatedAt: "+T.updatedAt);
			System.out.println("==============================================");
		}
		
	}
	
	static public void updateTask(File f,int id,String newTask) {
		tasks = tasksList(f);
		LocalDateTime dateTime = LocalDateTime.now(); //get the time now
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //format it
		String now = dateTime.format(myFormatObj); //assign it to the attribute
		
		
		for(Task T: tasks) {
			if(T.id == id) {
				T.setDescription(newTask); 
				T.setUpdatedat(now); 
			}
		}
	
		WriteFile(f,tasks);
	
		
		
	}
}
