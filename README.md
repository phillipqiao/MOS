# ***Mini-Ordering System (MOS)***  

#### - *Phillip Qiao's Personal Project (CPSC 210)*  

### Project Intentions:  

With the advent of technology and social media, an increasing number of new business owners have benefited from the 
convenience of online platforms for advertising, such as Instagram and Facebook. The easy accessibility of these social
media platforms has made it more achievable for beginners to start a new business. *I am particularly interested in 
developing an ordering system application that can take the next steps in recording and tracking the orders received 
by business owners.* However, the target users for my application are not limited to those who are planning to start 
their own business and find a digital organizer more efficient than a physical notebook. With a few tweaks,
I have found my application to be more useful in many smaller daily events, such as creating a party 
cuisine signup sheet and pre-ordering for homemade goods sale. *(On a personal level, I hope this application can be 
useful for my Dad's restaurant in taking orders.)*

### What is MOS?  

The ***Mini-Ordering System (MOS)*** is an application designed to assist business owners in organizing the customer 
orders they receive for their shop. Firstly, this application allows users, typically shop owners, to add and remove 
their merchandise under their shop's menu. When prompted, MOS can list all the items a shop currently has for sale,
which can be displayed to its customers. In a scenario where a user receives an order, MOS allows the user to keep 
a record of the customer's order. Further, MOS can provide the user with a list of items a customer has purchased. 
Finally, MOS can checkout a customer after their order has been fulfilled.  

### User Stories :  

- As a user, I want to be able to **create** a new merchandise(item) and **add** it to a shop’s for-sale list(menu),
  specifying the name, price, and stock of the item.  


- As a user, I want to be able to **remove** a item from the shop’s menu.  


- As a user, I want to be able to **view a list** of all items in the menu.


- As a user, I want to be able to select a merchandise and **add** a new customer who purchased that item.


- As a user, I want to be able to select a customer and **view a list** of all the items that customer has 
  purchased.  


- As a user, I want to be able to select a customer and **remove** (checkout) that customer from all the items 
  he/she has purchased.


- As a user, I want to have the option to **save** my menu and the state of the application.  


- As a user, I want to have the option to **reload** my saved state of application (Console version only).  


- As a user, I want to be prompted with the option to **reload** my saved state of application when 
  the application starts (GUI version only). 


### Instructions for Grader:

- You can generate the first required action related to the user story "adding multiple Xs to a Y" **(Displaying all 
  Items that have already been added to the Menu)** by : pressing the **"Display Menu" button** in the Owner Tab 
  and pressing the **"Refresh Menu" button** in the Customer Tab.  


- You can generate the second required action related to the user story "adding multiple Xs to a Y"
  **(add an Item to the Menu)** by : **entering** the corresponding information (name, price, stock of the item) 
  of the item in the text fields located on the right side of the Owner Tab, and then press the **"Add Item" button**.
  You can also add an item to a customer's purchase list by **entering** the corresponding information
  (customer name and the name of the item) on the right side of the Customer Tab, and then press
  the **"Order Item" button**.  


- You can generate the third required action related to the user story "adding multiple Xs to a Y"
  **(displays the subset of the Items (from the menu) that is in the purchases list of a customer)** by : 
  **entering** the information of the customer (name) in the corresponding text field and then press the
  **"View Order" button** in the Customer Tab. (Note: first time customer needs to add a few items to their
  purchase list first, see bullet point above)  


- You can locate my visual components by clicking on the Customer Tab on the left, and by pressing the **"View Order"
  button**.  


- You can save the state of my application by selecting the **"File" option** in the Menu bar, then select **"Save"**.  


- You can reload the state of my application by selecting the **"Yes" option** from the "Load or not"
  popup Option window after the application runs.  



### Phase 4: Task 2

**Sample Log print out:**

Wed Nov 29 12:26:02 PST 2023 <br>
Item : burger added to the menu!

Wed Nov 29 12:26:07 PST 2023 <br>
Item : burger removed from the menu!

Wed Nov 29 12:26:16 PST 2023 <br>
Item : fries added to the menu!

Wed Nov 29 12:26:28 PST 2023 <br>
Customer phil has ordered fries!

Wed Nov 29 12:26:30 PST 2023 <br>
Customer : phil has been checked out!

### Phase 4: Task 3

- If I had more time to work on the project, I would address the issue of location and instantiate the OwnerTab,
CustomerTab, and ConsolePrinter instances in the constructor of my MiniOrderingSystemGUI class, making them fields. 
This adjustment would transform the dependency into an association/bidirectional relationship. The rationale 
behind this lies in the fact that these three classes are consistently instantiated when the GUI program runs. 
Therefore, it makes sense to designate them as fields for the sake of consistency when reusing them in other methods 
of the MiniOrderingSystemGUI class. This modification would also enhance code readability, clearly indicating 
a long-term relationship among the classes and allowing their persistence beyond the scope of individual 
methods as part of the class's state.


- Another refactoring I would undertake pertains to my Menu class, where a considerable amount of repetitive 
code can be refactored and streamlined by implementing helper methods that perform the desired function based on 
the parameter passed in (e.g., addItem()/addCustomer(), containsItem()/containsCustomer(), 
selectItem()/selectCustomer()). Additionally, I would alter the data structure used by the Customer class 
to store its purchases list. Instead of a List<String>, a more structured approach could involve 
using a Map<String, Integer> to store the item names (String) and the quantity ordered (Integer) in 
a more organized manner.


