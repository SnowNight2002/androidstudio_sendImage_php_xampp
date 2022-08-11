<?php
header("Content-type: text/html; charset=utf-8");


  include "MySQL.class.php";

  if(!empty($_REQUEST['fid'])){

	$fid = $_REQUEST['fid'];// 接收GET请求的参数
 
   $sql= "SELECT * from tc_uploadfile where fid='{$fid}'";
  
   $db = new MySQL();
  
   $res = $db->getrow($sql);

   $result = json_encode($res);

   echo $result;
   
  }else{
	$sql= "SELECT * from tc_uploadfile ";
  
   $db = new MySQL();
  
   $res = $db->getall($sql);

   $result = json_encode($res);

   echo $result;
  }

?>