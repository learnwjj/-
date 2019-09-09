package Disk;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * �洢һ�����ļ�����
 */
public class FileData implements Serializable{
	 public static int TXT=0;//�ĵ�
     public static int FLODER=1;//�ļ���
     public static int EXECUTABL=2;//��ִ���ļ�
     public static int FAT=3;//���ļ�
	  //�����FAT�������̿��contextû�����壬������ļ�������context�������·�����ʣ�������ĵ�����context��������
	 private ArrayList<Integer> usingDiskNum = new ArrayList<>();//��¼�ļ�ռ�õĴ��̺����飬�����Ⱥ�˳��
	 private ArrayList<String> FileNameUnderFloder = new ArrayList<>();//��¼�ļ��������ļ�����������ļ����ƣ���ɾ���ļ���ʱ���Ӧɾ��Ŀ¼��ȫ���ļ�ʱ��
	 private String FileName;//�����ڲ��洢���ļ����ƣ�����·�������ƣ�����·��
     private int ListNum=0;//������ļ����ļ�ר�������ݣ��������͵��ļ����ù��������û������,ֻ��¼�ļ�����ֱ�ӵ��ļ�����Ӽ�ֱ�ӵ��ļ����Ǹ������¼ÿ�������Ŀ¼������ļ��ͼ�1������ܳ�8��������Ҫ��ӷ������
     private String Context;//���̵�Ԫ����洢�����ݣ�ֻ���ı��ļ������壩
     private int FileKind=this.TXT;//�ļ����ͣ�������һ�����ļ��У��ı��ļ��������ļ�,FAT���ֱ���0��1��2,3��ʾ�����ڽ�����ȫ�־�̬������ʾ��
//����ûʲô��     private int useBasicDiskElement=0;//��¼�Ѿ�ʹ�õĴ��̿���
     //Ĭ���ļ���txt��
     /*
      * һ��ʼ�����һ��ʹ�þ��Զ�����256�������������飬Ȼ���Ժ��ֱ�Ӷ�ȡ����
      */
     public FileData(int num,String name,String context) {//����txt�ļ�
   	  this.usingDiskNum.add(num);//������
   	  this.FileName=name;
      this.FileKind=this.TXT;
   	  this.Context=context;
   	  //this.useBasicDiskElement=1;
   	  
     }
     public FileData(int num,String name) {//�����ļ����ļ�
      	  this.usingDiskNum.add(num);//������
      	  this.FileName=name;
          this.FileKind=this.FLODER;
      	  this.Context="";
      	  this.ListNum=0;
      	 // this.useBasicDiskElement=1;
        }
     //Ӧ��û�����public������д����
     public FileData(int num,String name,Boolean a) {//���ɿ�ִ���ļ�������FAT�ļ���һ��Boolean��true���ǿ�ִ�У���false����FAT
    	 //this.useBasicDiskElement=1; 
    	 if(a) {
    	  this.usingDiskNum.add(num);//������
     	  this.FileName=name;
          this.FileKind=this.EXECUTABL;
     	  this.Context="";
     	  }
     	  else {
     		 this.usingDiskNum.add(num);//������
        	  this.FileName=name;
             this.FileKind=this.FAT;
        	  this.Context="";
     	  }
     	  
       } 
     public Boolean WhetherContextOverLoad(String newContext) {
   	  //�жϲ���һ����Ĵ�С
   	  Boolean result=true;
   	  if(newContext.getBytes().length>64)//�ж��ֽ����Ƿ����64�ֽ�
   		  result=false;
   	  return result;
     }
    
//     //��ȡ���txt�����ļ���contextռ�ݵ��ֽڴ�С,ֻ���ı��ļ�������
//     public int getTxtkindFileByteSize() {
// 		return this.Context.getBytes().length;
// 	}
//   //��ȡ���txt�����ļ���contextռ�ݵ��ֽڴ�С,ֻ���ı��ļ�������
//     public int getFloderkindFileByteSize() {
// 		return 8*this.ListNum;
// 	}
//     //��ִ���ļ���Ĭ����64byte��
     //�ϲ������������ȡ�ļ���С�ĺ���
     public int getFileByteSize() {
    	 if(this.FileKind==this.TXT) {//������ı��ļ�
    		 return this.Context.getBytes().length;
    	 }
    	 else if(this.FileKind==this.FLODER) {//������ļ�������
    		 return 8*this.ListNum;
    	 }
    	 else {//����ǿ�ִ���ļ�����
    		 return 64;
    	 }
     }

     //get��set
     
	public ArrayList<Integer> getUsingDiskNum() {
		return usingDiskNum;
	}
	public void addUsingDiskNum(int num) {
		this.usingDiskNum.add(num);
	}
	public void ObjDeleteUsingDiskNum(int num) {//�����ɾ������num��Ԫ��
		this.usingDiskNum.remove(Integer.valueOf(num));
	}
	public void indexDeleteUsingDiskNum(int num) {//�����ɾ��num�±��Ԫ��
		this.usingDiskNum.remove(num);
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public int getFileKind() {
		return FileKind;
	}
	public void setFileKind(int fileKind) {
		FileKind = fileKind;
	}
	public int getListNum() {
		return ListNum;
	}
	public void setListNum(int listNum) {
		ListNum = listNum;
	}
//	public int getUseBasicDiskElement() {
//		return useBasicDiskElement;
//	}
//	public void setUseBasicDiskElement(int useBasicDiskElement) {
//		this.useBasicDiskElement = useBasicDiskElement;
//	}
	public ArrayList<String> getFileNameUnderFloder() {
		return FileNameUnderFloder;
	}
	public void addFileNameUnderFloder(String name) {
		this.FileNameUnderFloder.add(name);//��ӵ��ļ�����
	}
	public void deleteFileNameUnderFloder(String name) {
		//Ϊ�˷�ֹ���г���������Ҫ������ļ����ƣ��Ż�����
		if(this.FileNameUnderFloder.contains(name)) {
		this.FileNameUnderFloder.remove(name);//ɾ���ļ��������Ӧ���ļ���
		}	
	}
 
}
