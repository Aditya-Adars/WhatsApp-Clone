<?php
include('connection.php')
?>

<html lang='en'>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, intial-scale=1.0">
        <tittle>Document</tittle>
    </head>
    <body>
        <form actio="adi.php" method="post">
            <input type="text" name="name" placeholder="name">
            <input type="text" name="email" placeholder="email">
			<input type="int"  name="password" placeholder="password">
			<input type="int"  name="phone number" placeholder="phone number">


            <input type="submit" name="submit" value="submit">
        </form>
    </body>
</html>

<?php
if(isset($_POST['submit'])){
    $name=$_POST['name'];
    $email=$_POST['email'];
    $password=$_POST['password'];
	$phonenumber=$_POST['phone number'];


    if($name!='' && $email!='' && $password!='' && $phonenumber!=''){
        $q="INSERT INTO party VALUES('$name','$email','$password', '$phonenumber')";
        $data=mysqli_qyery($conn,$q);

        if($data){
            echo"congo";

        }else{
            echo"not working";
        }
    }
}

