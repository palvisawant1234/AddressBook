package add;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class AddressBook extends BubbleSort {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
    	System.out.println("Welcome to AddressBook");
        while (true) {
            System.out.println("1.Create New Addressbook \n2.Add Person\n3.Delete Person \n4.Modify Person \n5.Sort \n6.Search Person \n7.Read Addressbook \n8.Exit");
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1:System.out.println("It will overwrite the previous contents \n Do you want to continue? Y or N");
                String choose = sc.nextLine();    
                if(choose.equals("y"))
					try {
						create();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    break;
                case 2:
				try {
					add();
				} catch (IOException e) {
					e.printStackTrace();
				}
                    break;
                case 3:
                	System.out.println("Enter Pno to delete");
                    String n = sc.nextLine();
                    delete(n);
                    System.out.println("Deleted Successfuly");
                    break;
                case 4:
                	System.out.println("Enter Pno to modify details");
                    String no = sc.nextLine();
                    modify(no);
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
        JSONObject addressbook = new JSONObject();
        JSONArray data = new JSONArray();
        JSONArray address = new JSONArray();
        JSONObject person = new JSONObject();
        JSONObject addressobj = new JSONObject();
        System.out.println("Enter name");
        person.put("name", sc.nextLine());
        System.out.println("Enter Apartment name");
        addressobj.put("aprt", sc.nextLine());
        System.out.println("Enter City");
        addressobj.put("city", sc.nextLine());
        System.out.println("Enter State");
        addressobj.put("state", sc.nextLine());
        System.out.println("Enter Zipcode");
        addressobj.put("zip", Long.parseLong(sc.nextLine()));
        System.out.println("Enter PhoneNo");
        person.put("pno", Long.parseLong(sc.nextLine()));
        data.add(person);
        address.add(addressobj);
        person.put("Address", address);
        addressbook.put("data", data);
        writeFile(addressbook.toString());
    }
    
    static void writeFile(String s) throws IOException {
        FileWriter fw = new FileWriter("book.json");
        fw.write(s);
        fw.close();
    }

    static void read() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("book.json"));
            JSONObject addressbook = (JSONObject) obj;
            System.out.println(obj.toString());
        } catch (Exception e) {
        }
    }


    static void add() throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(new FileReader("book.json"));
            JSONObject addressbook = (JSONObject) o;
            JSONArray data = (JSONArray) addressbook.get("data");
            JSONObject person = new JSONObject();
            JSONObject addressobj = new JSONObject();
            JSONArray address = new JSONArray();
            System.out.println("Enter name");
            person.put("name", sc.nextLine());
            System.out.println("Enter Apartment name");
            addressobj.put("aprt", sc.nextLine());
            System.out.println("Enter City");
            addressobj.put("city", sc.nextLine());
            System.out.println("Enter State");
            addressobj.put("state", sc.nextLine());
            System.out.println("Enter Zipcode");
            addressobj.put("zip", Long.parseLong(sc.nextLine()));
            System.out.println("Enter PhoneNo");
            person.put("pno", Long.parseLong(sc.nextLine()));
            data.add(person);
            address.add(addressobj);
            person.put("Address", address);
            writeFile(addressbook.toString());
        } catch (Exception e) {
        }
    }

    
    static boolean delete(String n) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("book.json"));
            JSONObject addressbook = (JSONObject) obj;
            String str = addressbook.toString();
            String s1 = "";
            JSONArray data = (JSONArray) addressbook.get("data");
            Iterator i = data.iterator();
            while (i.hasNext()) {
                JSONObject person = (JSONObject) i.next();
                if (person.get("pno").toString().equals(n) == true) {
                    s1 += person.toString();
                    break;
                } else if (person.get("pno").toString().equals(n) == false && i.hasNext() == false) {
                    System.out.println("No such element present");
                    return false;
                }
            }
            String str3 = str.replace(s1, "");
            Object updated = parser.parse(str3);
            JSONObject newaddressbook = (JSONObject) updated;
            FileWriter fs = new FileWriter("book.json");
            fs.write(newaddressbook.toJSONString());
            fs.close();
        } catch (Exception e) {
        }
        return true;
    }

    static void modify(String n) {
        try {
            if (delete(n) == true) {
            	System.out.println("Enter updated details");
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
            JSONObject addressbook = (JSONObject) o;
            String str = addressbook.toString();
            JSONArray data = (JSONArray) addressbook.get("data");
            str3 = new String[data.size()];

            for (int i = 0; i < data.size(); i++) {

                if (s.equals("name") || s.equals("pno")) {
                    JSONObject person = (JSONObject) data.get(i);
                    str3[i] = person.get(s).toString().toLowerCase();
                } else {

                    JSONObject person = (JSONObject) data.get(i);
                    JSONArray ja = (JSONArray) person.get("Address");
                    JSONObject oo = (JSONObject) ja.get(0);
                    str3[i] = oo.get(s).toString().toLowerCase();
                }
            }

            BubbleSort b = new BubbleSort();
            b.bubble(str3, str3.length);
            JSONObject newobj = new JSONObject();
            JSONArray newarr = new JSONArray();
            
            for (int i = 0; i < str3.length; i++) 
            {
                for (int j = 0; j < str3.length; j++) {

                    JSONObject anothernewobj = (JSONObject) data.get(j);
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
            JSONObject addressbook = (JSONObject) o;
            String str = addressbook.toString();
            JSONArray data = (JSONArray) addressbook.get("data");
            System.out.println("Enter " + s + " you want to search");
            String search = sc.nextLine().toLowerCase();
            boolean b = false;
            for (int i = 0; i < data.size(); i++) {
                JSONObject person = (JSONObject) data.get(i);
                JSONArray address = (JSONArray) person.get("Address");
                JSONObject addressob = (JSONObject) address.get(0);
    
                if (person.get("name").toString().toLowerCase().equals(search)
                        || person.get("pno").toString().toLowerCase().equals(search)
                        || addressob.get("zip").toString().toLowerCase().equals(search)
                        || addressob.get("state").toString().toLowerCase().equals(search)
                        || addressob.get("aprt").toString().toLowerCase().equals(search)
                        || addressob.get("city").toString().toLowerCase().equals(search)) {
                    b = true;           
                    System.out.println("Name :" + person.get("name"));
                    System.out.println("PhoneNo :" + person.get("pno"));
                    System.out.println("Zip :" + addressob.get("zip"));
                    System.out.println("Apartment :" + addressob.get("aprt")); 
                    System.out.println("City :" + addressob.get("city"));
                    System.out.println("State :" + addressob.get("state"));
                }
            }
            if (b == false) {
                System.out.println("Record not found");
            }
        } catch (Exception e) {
        }
    }
}

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
