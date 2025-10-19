create DATABASE IF not EXISTS billingsystem;


USE billingsystem;

CREATE TABLE IF NOT EXISTS Bill (
    BillID INT AUTO_INCREMENT PRIMARY KEY,   
    BillPayer VARCHAR(100) NOT NULL,        
    Role VARCHAR(50) NOT NULL,             
    Phone VARCHAR(15) NOT NULL,              
    Amount DECIMAL(10, 2) NOT NULL,        
    RestOfBill DECIMAL(10, 2) NOT NULL       
    
);


CREATE TABLE IF NOT EXISTS Payer (
    PayerID INT AUTO_INCREMENT PRIMARY KEY,   
    Name VARCHAR(100) NOT NULL,               
    Phone VARCHAR(15) NOT NULL,               
    Role VARCHAR(50) NOT NULL,          
    Department VARCHAR(50) NOT NULL,         
    Amount DECIMAL(10, 2) NOT NULL,          
    RestOfBill DECIMAL(10, 2) NOT NULL      
);

select * from Bill ;

select * from Payer ;
