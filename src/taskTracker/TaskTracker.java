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
	private static ArrayList<Task> tasks = new ArrayList<Task>();
	private static String[] arguments; 
	public static void main(String[] args) {
		File tasksFile = createFile(); 
		Scanner scanner = new Scanner(System.in);
		String s;
		
			 s =  (String)scanner.nextLine();
			 while( !s.equals("exit")) {
				 s = scanner.nextLine();
				 arguments = s.split(" ");
				 switch(arguments.length) {
				 	case 1	:	switch(arguments[0]) 
				 			{
				 					case "list"	:	listallTasks(tasksFile);
				 									break;
				 					default		: 	System.out.println("instruction introuvable");
				 									System.out.println("tap help for instructions use");
				 			}
				 					break;
				 	case 2	:	switch(arguments[0]) 
				 			{
					 				case "add" 		:	if(arguments[1] instanceof String) 
					 									{
						 									Task t = new Task(arguments[1]);
						 									addTask(tasksFile,t);
					 									}else {
						 									System.out.println("argument need to be string");
						 									System.out.println("tap help for instructions use");
					 									}
					 									break;
					 									
					 				case "delete"	:	
					 								try {
					 									int x = Integer.parseInt(arguments[1]);
				 										deleteTask(tasksFile,x); 
					 								}catch(Exception e){
					 									System.out.println("argument needs to be a number");
					 									System.out.println("tap help for instructions use");
					 								}
		 												break;
						 			case "list"	:	switch(arguments[1]) {
								 					case "done"			: 	listDoneTasks(tasksFile);
								 											break;
						 							case "in-progress"	:	listInProgTasks(tasksFile);	
						 													break;
						 						}
						 								break;
				 			}
				 					break;
				 	case 3	:		
				 					break;
				 					
				 	default : 	System.out.println("too much arguments");
								System.out.println("tap help for instructions use");
				 }
			}
			 scanner.close();
		
			

	}
	
	
	static public void help() {
		System.out.println("--------------------------------------------");
		
		System.out.println("list all tasks			: list");
		System.out.println("list in-progress tasks 	: list in-progress");
		System.out.println("list finished tasks		: list done");
		System.out.println("list unfinished tasks	: list todo");
		System.out.println("list in-progress tasks	: list in-progress");
		System.out.println("mark task finished		: mark-done id");
		System.out.println("mark task in-progress	: mark-in-progress id");
		System.out.println("list in-progress tasks 	: list in-progress");
		System.out.println("add task 				: add \"task\"");
		System.out.println("update task 			: update id \"task\"");
		System.out.println("delete task				: delete id  ");
		
		System.out.println("--------------------------------------------");
	
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
			WriteFile(f,tasks);	
		}
		System.out.println("Task added Successfully (ID : "+t.getId()+")");

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
	
	
	static public void mark(String status,File f,int id) {
		tasks = tasksList(f);
		for(Task T: tasks) {
			if(T.id == id) {
				T.setStatus(status);
			}
		}
		WriteFile(f,tasks);	
	}
	
	static public void listDoneTasks(File f) {
		tasks = tasksList(f);
		for(Task T: tasks) {
			if(T.getStatus() == "done") {
				System.out.println("id: "+T.id);
				System.out.println("description: "+T.description);
				System.out.println("status: "+T.status);
				System.out.println("createdAt: "+T.createdAt);
				System.out.println("updatedAt: "+T.updatedAt);
				System.out.println("==============================================");
			}
				
		}
		
	}
	
	static public void listTodoTasks(File f) {
		tasks = tasksList(f);
		for(Task T: tasks) {
			if(T.getStatus() == "todo") {
				System.out.println("id: "+T.id);
				System.out.println("description: "+T.description);
				System.out.println("status: "+T.status);
				System.out.println("createdAt: "+T.createdAt);
				System.out.println("updatedAt: "+T.updatedAt);
				System.out.println("==============================================");
			}
				
		}
	}
	
	static public void listInProgTasks(File f) {
		tasks = tasksList(f);
		for(Task T: tasks) {
			if(T.getStatus() == "in-progress") {
				System.out.println("id: "+T.id);
				System.out.println("description: "+T.description);
				System.out.println("status: "+T.status);
				System.out.println("createdAt: "+T.createdAt);
				System.out.println("updatedAt: "+T.updatedAt);
				System.out.println("==============================================");
			}
				
		}
	}
	
	static public void deleteTask(File f,int id) {
		tasks = tasksList(f);
		for(Task T: tasks) {
			if(T.id == id) {
				tasks.remove(T);
			}
		}
		WriteFile(f,tasks);	
	
	}
}
