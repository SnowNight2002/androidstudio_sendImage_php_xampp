<?php
header("Content-type: text/html; charset=utf-8");

include "MySQL.class.php";

if( !empty($_FILES['uploadfile']['name']) ){

	$db = new MySQL();

	$fields['dateline'] = time();
	$fields['userip'] = $_SERVER["REMOTE_ADDR"];// 用户IP


	if(!empty($_FILES['uploadfile']['name'])){
	    	$filename = 'uploadfile/' . $fields['dateline'].$_FILES['uploadfile']['name']; 
	    	move_uploaded_file($_FILES['uploadfile']['tmp_name'],$filename);
	    	$fields['uploadfile'] = $filename;
    	}

	$tid = $db->insert('tc_uploadfile',$fields);
	echo "{".$tid;
}else{

	echo "{0";
}

?>