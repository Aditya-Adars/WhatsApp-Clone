<?php
$servername ="localhost";
$username = "root";
$password = "";
$dbname = "aditya";




$conn = mysqli_connect($servername,$username,$password,$dbname);


if($conn)
{
	echo "connection ok";
}
else
{
	echo"failed.mysqli_connect_error()";
}

?>