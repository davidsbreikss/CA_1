First CCT Programming and Math CA.

Task description: 

When in operation, the program will be given a file named “customers.txt” – this contains the details of 
(fictitious) customers in the following format: 
Line 1 – <First Name> <Second Name> 
Line 2 – <Total Purchase> 
Line 3 – <Class> 
Line 4 - <Last Purchase> 
Your task is to: 
1) Read the data from the file and check that it is valid. The file may contain more than one customer, 
so an appropriate loop should be used. The data must obey the following rules: 
a) The field first name must be letters only; 
b) The second name can be letters and/or numbers and must be separated from the first 
name by a single space; 
c) The field value of total purchase must be double and 
d) The field Class must be a integer between 1 to 3. 
e) Last purchase must be a valid year. 
2) If the data is not valid, you should output a useful error message on screen to the user. 
3) If the data is valid, then you have to calculate the discount to the net value and save a file named 
customerdiscount.txt, in the following format: 
<Name> - <Discount value> 
<Final Value> 
Where the <Final Value> is determined by the number of classes, as follows: 
Criteria Final Value
Classe = 1 and Last Purchase in 2024 Value Purchased - Discount of 30% 
Classe = 1 and Last Purchase before than 2024 Value Purchased - Discount of 20% 
Classe = 1 and no Purchase in the last 5 years Value Purchased - Discount of 10% 
Classe = 2 and Last Purchase in 2024 Value Purchased - Discount of 15% 
Classe = 2 and Last Purchase before than 2024 Value Purchased - Discount of 13 % 
Classe = 2 and no Purchase in the last 5 years Value Purchased - Discount of 5 % 
Classe = 3 and Last Purchase in 2024 Value Purchased - Discount of 3 % 
Classe = 3 and Last Purchase before than 2024 Value Purchased - Discount of 0 % 


What I have accomplished:
1. Automatically determine what kind of file is being read: txt, csv, json.
2. Processing incoming data and manipulate it based on requirments.
3. Writing final data to a new file.
4. Covered cruicial code by unit tests.
5. Added comment

