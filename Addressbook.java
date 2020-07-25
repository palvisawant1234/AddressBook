import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class BubbleSort
{
	public static <P extends Comparable<P>> void bubble(P[] arr,int length) 
	{	
		int n=length;
		for(int i=0;i<n-1;i++) 
		{
			for(int j=0;j<n-i-1;j++)
			{
				if(arr[j].compareTo(arr[j+1])>0)
				{
					P temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
			
		}
		for(int x=0;x<n;++x)
		{
			System.out.print(arr[x]+" ");
			System.out.println();
		}
	}
}  

class AddressBook extends BubbleSort {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
    	System.out.println("Welcome to AddressBook");
        while (true) {
            System.out.println("1.Create \n2.Add \n3.Delete \n4.Modify \n5.sort \n6.Search \n7.Read \n8.Exit");
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1:System.out.println("It will overwrite the previous contents \n Do you want to continue? Y or N");
                String choose = sc.nextLine();    
                if(choose.equals("y"))
					try {
						create();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    break;
                case 2:
				try {
					add();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case 3:
                    delete();
                    break;

                case 4:
                    modify();
                    break;
                case 5:
                    System.out.println("Enter column to sort by:");
                    String s = sc.nextLine();
                    sort(s);
                    break;
                case 6:
                    System.out.println("Search by:");
                    String s1 = sc.nextLine();
                    search(s1);
                    break;
                case 8:
                    System.exit(1);
                case 7:
                	read();
                	break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void create() throws IOException {
        JSONObject obj = new JSONObject();
        JSONArray arr1 = new JSONArray();
        JSONArray arr2 = new JSONArray();
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        System.out.println("Enter name");
        obj1.put("name", sc.nextLine());
        System.out.println("Enter Apartment name");
        obj2.put("aprt", sc.nextLine());
        System.out.println("Enter City");
        obj2.put("city", sc.nextLine());
        System.out.println("Enter State");
        obj2.put("state", sc.nextLine());
        System.out.println("Enter Zipcode");
        obj2.put("zip", Long.parseLong(sc.nextLine()));
        System.out.println("Enter PhoneNo");
        obj1.put("pno", Long.parseLong(sc.nextLine()));
        arr1.add(obj1);
        arr2.add(obj2);
        obj1.put("Address", arr2);
        obj.put("data", arr1);
        writeFile(obj.toString());
    }
    
    static void writeFile(String s) throws IOException {
        FileWriter fw = new FileWriter("book.json");
        fw.write(s);
        fw.close();
    }

    static void read() {
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(new FileReader("book.json"));
            JSONObject jobj = (JSONObject) o;
            System.out.println(o.toString());
        } catch (Exception e) {
        }
    }


    static void add() throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(new FileReader("book.json"));
            JSONObject obj = (JSONObject) o;
            JSONArray arr = (JSONArray) obj.get("data");
            JSONObject obj1 = new JSONObject();
            JSONObject obj2 = new JSONObject();

            JSONArray arr2 = new JSONArray();
            System.out.println("Enter name");
            obj1.put("name", sc.nextLine());
            System.out.println("Enter Apartment name");
            obj2.put("aprt", sc.nextLine());
            System.out.println("Enter City");
            obj2.put("city", sc.nextLine());
            System.out.println("Enter State");
            obj2.put("state", sc.nextLine());
            System.out.println("Enter Zipcode");
            obj2.put("zip", Long.parseLong(sc.nextLine()));
            System.out.println("Enter PhoneNo");
            obj1.put("pno", Long.parseLong(sc.nextLine()));

            arr.add(obj1);
            arr2.add(obj2);
            obj1.put("Address", arr2);
            // obj1.put("Address", arr2);
            // obj.put("data", arr);
            writeFile(obj.toString());
        } catch (Exception e) {
        }
    }

    
    static boolean delete() {
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(new FileReader("book.json"));
            JSONObject jobj = (JSONObject) o;
            String str = jobj.toString();
            String s1 = "";
            System.out.println("Enter Pno to delete");
            String n = sc.nextLine();
            JSONArray arr = (JSONArray) jobj.get("data");
            Iterator i = arr.iterator();
            while (i.hasNext()) {
                JSONObject o1 = (JSONObject) i.next();
                System.out.println(o1.get("pno"));
                if (o1.get("pno").toString().equals(n) == true) {
                    s1 += o1.toString();
                    System.out.println(s1 + "Deleted");
                    break;
                } else if (o1.get("pno").toString().equals(n) == false && i.hasNext() == false) {
                    System.out.println("No such element present");
                    return false;
                }
            }
            String str3 = str.replace(s1, "");
            Object o4 = parser.parse(str3);
            JSONObject jobject1 = (JSONObject) o4;
            FileWriter fs = new FileWriter("book.json");
            fs.write(jobject1.toJSONString());
            fs.close();
        } catch (Exception e) {
        }
        return true;
    }

    static void modify() {
        try {
            if (delete() == true) {
                add();
            } else {
                System.out.println("Element not found");
            }
        } catch (Exception e) {
        }
    }

    static void sort(String s) {
        String str3[];
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(new FileReader("book.json"));
            JSONObject jobj = (JSONObject) o;
            String str = jobj.toString();
            JSONArray array = (JSONArray) jobj.get("data");
            str3 = new String[array.size()];

            for (int i = 0; i < array.size(); i++) {

                if (s.equals("name") || s.equals("pno")) {
                    JSONObject o1 = (JSONObject) array.get(i);
                    str3[i] = o1.get(s).toString().toLowerCase();
                } else {

                    JSONObject o1 = (JSONObject) array.get(i);
                    JSONArray ja = (JSONArray) o1.get("Address");
                    JSONObject oo = (JSONObject) ja.get(0);
                    str3[i] = oo.get(s).toString().toLowerCase();
                    System.out.println("ASdasdasdas");
                }
            }

            BubbleSort b = new BubbleSort();
            b.bubble(str3, str3.length);
            JSONObject newobj = new JSONObject();
            JSONArray newarr = new JSONArray();
            
            for (int i = 0; i < str3.length; i++) 
            {
                for (int j = 0; j < str3.length; j++) {

                    JSONObject anothernewobj = (JSONObject) array.get(j);
                    if (s.equals("name") || s.equals("pno")) {
                        if (str3[i].equals(anothernewobj.get(s).toString().toLowerCase()) == true) {
                            newarr.add(anothernewobj);
                        }
                    } else {
                        JSONArray jarr1 = (JSONArray) anothernewobj.get("Address");
                        JSONObject jobj1 = (JSONObject) jarr1.get(0);
                        System.out.println(jobj1.toString());
                        if (str3[i].equals(jobj1.get(s).toString().toLowerCase()) == true) {
                            newarr.add(anothernewobj);
                        }
                    }
                }

            }
            newobj.put("data", newarr);
            System.out.println(newobj.toString());
            FileWriter fw = new FileWriter("book1.json");
            fw.write(newobj.toJSONString());
            fw.close();
        } catch (Exception e) {
        }
    }

    static void search(String s) {
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(new FileReader("book.json"));
            JSONObject jobj = (JSONObject) o;
            String str = jobj.toString();
            JSONArray array = (JSONArray) jobj.get("data");
            System.out.println("Enter " + s + " you want to search");
            String search = sc.nextLine().toLowerCase();
            boolean b = false;
            for (int i = 0; i < array.size(); i++) {
                JSONObject jobj1 = (JSONObject) array.get(i);
                JSONArray arr = (JSONArray) jobj1.get("Address");
                JSONObject ob = (JSONObject) arr.get(0);
    
                if (jobj1.get("name").toString().toLowerCase().equals(search)
                        || jobj1.get("pno").toString().toLowerCase().equals(search)
                        || ob.get("zip").toString().toLowerCase().equals(search)
                        || ob.get("state").toString().toLowerCase().equals(search)
                        || ob.get("aprt").toString().toLowerCase().equals(search)
                        || ob.get("city").toString().toLowerCase().equals(search)) {
                    b = true;
           
                    System.out.println("Name :" + jobj1.get("name"));
                    System.out.println("PhoneNo :" + jobj1.get("pno"));
                    System.out.println("Zip :" + ob.get("zip"));
                    System.out.println("Apartment :" + ob.get("aprt")); 
                    System.out.println("City :" + ob.get("city"));
                    System.out.println("State :" + ob.get("state"));
                }
            }
            if (b == false) {
                System.out.println("Record not found");
            }
        } catch (Exception e) {
        }
    }
}
