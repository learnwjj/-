package Disk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class main {
      public static void main(String [] args) throws ClassNotFoundException, IOException {
    	  FileOperation fileoperation=new FileOperation();
    	  
    	  fileoperation.SavingDiskAndFATFile();
    	 //�����ļ��Ĵ����ͱ�����
    	  //fileoperation.createFLODERFile("ROOT", "c");
    	  //fileoperation.createTXTFile("ROOT\\c", "a");
    	  //fileoperation.createEXEFile("ROOT\\c","E");
    	  //fileoperation.changeTxtFileContext("11111111111111111111111111111111111111111111111111111111111111111","ROOT\\c\\a");
    	  //fileoperation.copyTxtOrExeFile("ROOT\\c\\a","ROOT");
    	  //fileoperation.deleteFloderFile("ROOT\\c");
    	  fileoperation.changeFileOrFloderName("b", "ROOT\\c\\a");
    	  outputFileDataGroup(fileoperation);
    	  fileoperation.SavingDiskAndFATFile();
    	  showFAT(fileoperation.getFATformat());

      }
      
      public static void outputFileDataGroup(FileOperation fileoperation) {
    	  System.out.println("�ļ�����������ļ�");
    	  for(int i=0;i<fileoperation.getFileDataGroup().size();i++) {
    		  System.out.println("�ļ� ");
    		  System.out.println(fileoperation.getFileDataGroup().get(i).getFileName());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileKind());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileByteSize());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getUsingDiskNum());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileNameUnderFloder());
    	  }
      }
      public static void showFAT(FAT fat) {
    	  System.out.println("FAT���б�����Ŀ��");
    	  for(int i=0;i<256;i++) {
    		  if(fat.getDiskState()[i]!=0) {
    			  System.out.println("���"+i+" ����"+fat.getDiskState()[i]);
    		  }
    	  }
      }
}
