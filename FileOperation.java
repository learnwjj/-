package Disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*
 * �������ļ��洢����src\Disk���·������һ��FAT���ļ���FAT���һ��FileData���ļ���ÿ���������ļ��Ĵ�С�������ֵ�����
 * �ļ��Ĵ�ȡֻ��Ҫ����������Ŀ����͹ر�ʱ�����㼴��
 *ģ����̵ĸ�Ŀ¼��root\
 *
 *֮ǰ�뵽�ڴ�����ʱ�����ʾ���⣬��Ϊ�и�Ŀ¼���������Ǹ��ָ�ȡ����һ���ļ��о���·�����ķ�������һ�����ʾ���ļ��к��ļ�֮��Ĺ�ϵ��
 *���ɵ�ʱ����Է�����������ɨ��ȫ�����ı��ļ������ļ�����Ԫ�غܼ򵥣�Ȼ����ɨ�����ļ���ʾ����
 *
 *����һЩ�鷳���������ļ���ͬ����Ҳ��������
 *
 *
 *���ƣ�����Ŀ���ἰ���Ǹ����ļ�������ֻʵ�ֶ���һ���ļ��ĸ��ƣ��ļ��в������ƣ���Ϊ�����ļ����漰�ļ�������̫���ļ��ĸ��ƣ��������Ʋ�̫�ʺ������ָ���
 root�ļ����Ǹ�Ŀ¼����ɾ����Ȼ������Ϊ������һЩ�߼���Ĭ�ϱ�����һ���ļ��еĴ��ڵ�
 */
public class FileOperation {
	   private ArrayList<FileData> FileDataGroup;
       private FAT FATformat;

       
       public ArrayList<FileData> getFileDataGroup() {
		return FileDataGroup;
	}


	public void setFileDataGroup(ArrayList<FileData> fileDataGroup) {
		FileDataGroup = fileDataGroup;
	}


	public FAT getFATformat() {
		return FATformat;
	}


	public void setFATformat(FAT fATformat) {
		FATformat = fATformat;
	}


	
	
	//��ʼ������
       public FileOperation() throws IOException, ClassNotFoundException{
    	  File FileDataFile = new File("src/Disk/FileData.txt");
    	  File FATFile = new File("src/Disk/FAT.txt");
    	  if(FileDataFile.exists()) {//�ж�һ�����ɣ���Ϊ��һ�𴴽�
       	    // ����ļ����ھͶ�ȡ����
    		  //�ȶ�ȡFAT
    		  FileInputStream fisFAT = new FileInputStream(FATFile);
    		  ObjectInputStream objIPFAT = new ObjectInputStream(fisFAT);
    		  this.FATformat= (FAT)objIPFAT.readObject();
    		  objIPFAT.close(); 
    		  
    		  //��ȡDisk
    		  FileInputStream fisDisk = new FileInputStream(FileDataFile);
    		  ObjectInputStream objIPDisk = new ObjectInputStream(fisDisk);
    		  this.FileDataGroup= (ArrayList<FileData>)objIPDisk.readObject();
    		  objIPFAT.close();
    		  
    	  }
    	  else {
    		  //��ʼ������Ĵ��̿�
    		  this.FileDataGroup= new ArrayList<>();
    		  this.FileDataGroup.add(new FileData(0,"FAT1",false));//����0��1Ĭ��FAT
    		  this.FileDataGroup.add(new FileData(1,"FAT2",false));
    		  this.FileDataGroup.add(new FileData(2,"ROOT"));//����2�Ǹ�Ŀ¼�ļ���
    		  //Ĭ��ʹ����
    		  //��ʼ��FAT
    		  this.FATformat=new FAT();
    		  
    		  //��Ϊ�ж��ļ�������������Ҫ�����ļ��������ݽ����ļ�
    		  
    		  //����FAT�ļ����洢����
    		  FileOutputStream fosFAT = new FileOutputStream(FATFile);
    		  ObjectOutputStream objOPFAT = new ObjectOutputStream(fosFAT);
    		  objOPFAT.writeObject(this.FATformat);
    		  objOPFAT.close();
    		  
    		  //����Disk�ļ��洢����
    		  FileOutputStream fosDisk = new FileOutputStream(FileDataFile);
    		  ObjectOutputStream objOPDisk = new ObjectOutputStream(fosDisk);
    		  objOPDisk.writeObject(this.FileDataGroup);
    		  objOPDisk.close();  
    	  }
       }
       
       
       //�˳�����ı��溯��������FAT����Disk���������ļ�
       public void SavingDiskAndFATFile() throws IOException, ClassNotFoundException{
    	  File FileDataFile = new File("src/Disk/FileData.txt");
     	  File FATFile = new File("src/Disk/FAT.txt");
    	   //����FAT�ļ����洢����
 		  FileOutputStream fosFAT = new FileOutputStream(FATFile);
 		  ObjectOutputStream objOPFAT = new ObjectOutputStream(fosFAT);
 		  objOPFAT.writeObject(this.FATformat);
 		  objOPFAT.close();
 		  
 		  //����Disk�ļ��洢����
 		  FileOutputStream fosDisk = new FileOutputStream(FileDataFile);
 		  ObjectOutputStream objOPDisk = new ObjectOutputStream(fosDisk);
 		  objOPDisk.writeObject(this.FileDataGroup);
 		  objOPDisk.close();  
       }
       //��ȡ�ַ����ֽڴ�С�İ취String.getBytes()length
       
       //�����ı��ļ�,һ�㴴���ļ���û������Ҫ�������ٸ�д���������û���޸����ݵ���Ҫ
         public Boolean createTXTFile(String road,String name) {//�ļ����ǷǾ���·������һ�����ʱ��Ӧ���Ƿֿ����ֺ�ǰ��·�������ɺ��ٺ�����
        	 Boolean result=false;//����п�λ����ͷ�������򷵻ؼ�
        	 Boolean whetherSameName=false;//���������true�������Ͳ���������Ĳ���ֱ���жϴ���ʧ��
        	 name=road+"\\"+name;//�õ�����·��������
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//�ж���û�о���·������һ���Ĵ���
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//��������޷���������ֱ�Ӵ���ʧ��
        	 int findnum;//��ʹ�õĴ��̷���
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//�����ȥFAT���Ӧ״̬�ֱ��-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// ��ȡroad��Ӧ�������ļ����ļ���Ȼ���������·�����Ƶ��ļ����ļ������FileNameUnderFloder��������ļ�������
        			 FileData txtFile=new FileData(i,name,"");
                	 this.FileDataGroup.add(txtFile);
                	 break; 
        		 }
        	 }
            }
             return result;
         }
       //�����ļ����ļ�
         public Boolean createFLODERFile(String road,String name) {//�����ļ����ڵ��ļ��еľ���·�����ļ������֣��Ǿ���·����
        	 Boolean whetherSameName=false;//���������true�������Ͳ���������Ĳ���ֱ���жϴ���ʧ��
        	 Boolean result=false;//����п�λ����ͷ�������򷵻ؼ�
        	 name=road+"\\"+name;//�õ�����·��������
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//�ж���û�о���·������һ���Ĵ���
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//��������޷���������ֱ�Ӵ���ʧ��
        	 int findnum;//��ʹ�õĴ��̷���
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//�����ȥFAT���Ӧ״̬�ֱ��-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// ��ȡroad��Ӧ�������ļ����ļ���Ȼ���������·�����Ƶ��ļ����ļ������FileNameUnderFloder��������ļ�������
        			 FileData FLODERFile=new FileData(i,name);
                	 this.FileDataGroup.add(FLODERFile);
                	 break; 
        		 }
        	  }
             }
             return result;
         }
  
       //������ִ���ļ��ļ�
         public Boolean createEXEFile(String road,String name) {
        	 Boolean whetherSameName=false;//���������true�������Ͳ���������Ĳ���ֱ���жϴ���ʧ��
        	 Boolean result=false;//����п�λ����ͷ�������򷵻ؼ�
        	 name=road+"\\"+name;//�õ�����·��������
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//�ж���û�о���·������һ���Ĵ���
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//��������޷���������ֱ�Ӵ���ʧ��
        	 int findnum;//��ʹ�õĴ��̷���
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//�����ȥFAT���Ӧ״̬�ֱ��-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// ��ȡroad��Ӧ�������ļ����ļ���Ȼ���������·�����Ƶ��ļ����ļ������FileNameUnderFloder��������ļ�������
        			 FileData EXEFile=new FileData(i,name,true);
                	 this.FileDataGroup.add(EXEFile);
                	 break; 
        		 }
        	  }
             }
             return result;
         }
       //�޸��ı��ļ�
       public Boolean changeTxtFileContext(String newContext,String txtFileAbsName) {//�����޸ĺ�������ݣ��Լ��ļ��ľ���·����
    	   //����ļ����Ͳ���txt������������̲���Ҳ�᷵��false
    	   //Ѱ�Ҷ�Ӧ�ļ�
    	   Boolean whetherFileIsTxt=false;//�ļ��Ƿ���txt�ļ���������Ǻ�������ֵ����false
    	   int fileGroupNum=0;//���������ļ�������������ļ����ڵ��±�
    	   for(int j=0;j<this.FileDataGroup.size();j++) {
    		   if(this.FileDataGroup.get(j).getFileName().equals(txtFileAbsName)) {
    			   fileGroupNum=j;
    			   break;
    		   }
    	   }
    		if(this.FileDataGroup.get(fileGroupNum).getFileKind()==0) {//0����TXT������ļ�����txt�ļ����������ֱ�ӽ�������   
    			whetherFileIsTxt=true;
    	        //�����ж��¾���������Ĵ��������ӻ��Ǽ����ˣ�Ȼ������������
    			int oldNeedDiskNum=0;
    		    int newNeedDiskNum=0;//������		
    			if((this.FileDataGroup.get(fileGroupNum).getFileByteSize()%64)==0&&this.FileDataGroup.get(fileGroupNum).getFileByteSize()!=0) {
        	       oldNeedDiskNum=(int)(this.FileDataGroup.get(fileGroupNum).getFileByteSize()/64);//�ɵ�����������̿���
  
    			}
    			else {
    				oldNeedDiskNum=(int)(this.FileDataGroup.get(fileGroupNum).getFileByteSize()/64)+1;
    			}
    			if((newContext.getBytes().length%64)==0&&newContext.getBytes().length!=0) {
    				newNeedDiskNum=(int)(newContext.getBytes().length/64);//�µ�����������̿���
    			}
    			else {
    				newNeedDiskNum=(int)(newContext.getBytes().length/64)+1;
    			}
    			
    			
    			
    			//���Դ���
//    			System.out.println("��"+oldNeedDiskNum);
//    			System.out.println("��"+newNeedDiskNum);
    	        //
    			
    			
    			//������������µ�������Ҫ������̣��µ����ݲ���Ҫ�������̣��µ����ݲ���Ҫԭ����ô�����
    	        if(oldNeedDiskNum<newNeedDiskNum) {//Ҫ����������
    	        	int additionDiskNumRequest=newNeedDiskNum-oldNeedDiskNum;//��Ҫ�ٸ�����ļ�����Ĵ��̿���
    	        	//��Ҫ�ж�FAT������������
    	        	int avaliableDiskTotalNum=0;//���õĴ�������Ŀ
    	        	int [] avaliableDiskNum=new int[additionDiskNumRequest];//�����һ��������ж��Ƿ��㹻��Ŀʱ��˳��һ���¼���������䲻���ڴ˱���
    	        	int recordNum=0;//��¼ѭ��ʱ�������¼�����Ŀ��ô���������ֻҪ��¼Ҫ�õ���������
    	        	for(int i=0;i<256;i++) {
    	        		if(this.FATformat.getDiskState()[i]==0) {
    	        			if(recordNum<additionDiskNumRequest) {//ֻ��¼��Ҫ����������Ŀ��ô��̺�
    	        			avaliableDiskNum[recordNum]=i;//��¼���õĴ��̺�
    	        			recordNum++;
    	        			}
    	        			avaliableDiskTotalNum++;
    	        		}
    	        	}
    	        	if(avaliableDiskTotalNum<additionDiskNumRequest) {
    	        		return false;//�����������˵���޷����䷵��false�����κ��޸�,ֱ�ӽ�������
    	        	}
    	        	for(int i=0;i<additionDiskNumRequest;i++) {//ѭ������
    	        		int lastDiskNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//�Ȼ�ȡǰһ���ſ�ĺ���
    	        		this.FATformat.setDiskState(lastDiskNum,avaliableDiskNum[i]);//���޸�FAT�е�ǰһ���ſ��״̬�֣�����ָ���·���ſ�
    	        		this.FATformat.setDiskState(avaliableDiskNum[i],-1);//���޸��´ſ��״̬�֣����-1
    	        		this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().add(avaliableDiskNum[i]);//����fileData��Ӧ�Ķ����usingDiskNum��������к����������ſ��
    	        	}
    	        	this.FileDataGroup.get(fileGroupNum).setContext(newContext);
    	        }
    	        else if(oldNeedDiskNum>newNeedDiskNum) {//Ҫ������ٴ���
    	        	//��Ϊ�Ǽ��پͲ���Ҫ�жϲ����ڴ��̵�����
    	        	//ֻ��Ҫ�����µ���Ҫ�Ĵ��̾ͺ���
    	        	int freeDiskNumRequest=oldNeedDiskNum-newNeedDiskNum;//��Ҫ�ͷŵĴ�����
    	        	for(int i=0;i<freeDiskNumRequest;i++) {//ѭ������
    	                  int endFileFATNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//�ҵ�FileData��UsingDiskNum��������һ����¼��FAT��
    	                  int beforeEndFileFATNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-2);//�ҵ�FileData��UsingDiskNum����ĵ����ڶ�����¼��FAT��
    	                  this.FATformat.setDiskState(endFileFATNum,0);//�޸�FATformat�����UsingDiskNum���һ�������״̬���0
    	                  this.FATformat.setDiskState(beforeEndFileFATNum,-1);//�޸�FATformat�����UsingDiskNum�����ڶ��������״̬���-1
    	                  this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().remove(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//ɾ��UsingDiskNum���һ��Ԫ��
    	        	}
    	        	this.FileDataGroup.get(fileGroupNum).setContext(newContext);
    	        }
    	        else {//����Ҫ�޸Ĵ��̣�ֱ���޸����ݼ���
    	        	this.FileDataGroup.get(fileGroupNum).setContext(newContext);
    	        }

    		}
    		return whetherFileIsTxt;
       }
       //�޸��ļ������ļ�������
       public void changeFileOrFloderName(String newName,String oldAbsName) {//��������һ�����޸ĺ�ķǾ������ƣ�һ�����޸�ǰ�ľ�������
    	   String [] splitOldName=oldAbsName.split("\\\\");//�ָ�
    	   String textName=splitOldName[splitOldName.length-1];//��ȡ�Ǿ��Ե�����
    	   String newAbsName=splitOldName[0];
    	   for(int i=1;i<splitOldName.length-1;i++) {
    		   newAbsName=newAbsName+"\\"+splitOldName[i];
    	   }
    	   newAbsName=newAbsName+"\\"+newName;//�µ��ļ��ľ���·����
    	   Boolean whetherSameName=false;//�Ƿ������ֺ;�����һ��
    	   if(textName.equals(newName)) {
    		   whetherSameName=true;
    	   }
    	   if(!whetherSameName) {
    		   String [] roadNameGroup=this.splitRoadToGetAllAboveFloder(oldAbsName);//�ָ�����һ���ַ����Ǿ����ֵľ���·���������Բ������
    	       for(int i=0;i<roadNameGroup.length-1;i++) {
    	    	   int FileGroupNum=0;
    	    	   int fileNameNum=0;
    	    	    for(int j=0;j<this.FileDataGroup.size();j++) {
    	    		   if(this.FileDataGroup.get(j).getFileName().equals(roadNameGroup[i])) {
    	    			   //�����������
    	    			   //System.out.println(this.FileDataGroup.get(j).getFileName()+"  "+roadNameGroup[i]);
    	    			   FileGroupNum=j;
    	    			   break;
    	    		   }
    	    		  } //��FileDataGroup�ҵ���Ӧ���Ƶ��ļ��ж���
    	    		for(int k=0;k<this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().size();k++) {
    	    			if(this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(k).equals(oldAbsName)) {
    	    				//System.out.println("�ڶ���for"+this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(k)+"  "+oldAbsName);
    	    				fileNameNum=k;
    	    				break;
    	    			}
    	    		}
    	    	  //�ҵ�����������������ֵ��±�
    	    	    
    	    	    //���Դ���
    	    	    //System.out.println("�޸ĵľ�����"+ this.FileDataGroup.get(FileGroupNum).getFileName()+" "+this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(fileNameNum));
    	    	    
    	    	    
    	    	    this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().set(fileNameNum,newAbsName);
    	    	    //�޸��ļ����¸��ļ����ļ�¼
    	       }
    	       for(int j=0;j<this.FileDataGroup.size();j++) {
	    		   if(this.FileDataGroup.get(j).getFileName().equals(oldAbsName)) {
	    			   this.FileDataGroup.get(j).setFileName(newAbsName);
	    			   break;
	    		   } 
    	            //�޸�����ļ���
    	          }
    	   }
       }
         
       //���ӻ��߼����ļ������ļ�����ֱ�����ļ�����һ�����ļ��������֣�����һ���Ƿ���ɾ���ļ�һ��ɾ������
       ////ע���������ֻ���޸�����·���ϵ��ļ��е������Ϣ���ļ��е�FAT�ķ������⣬���ǲ�������ӻ�ɾ�����ļ������ļ��������FileData������Ĵ�������ɾ��
         public Boolean addOrDeleteFileStraightToFloder(String fileName,String flodername,Boolean whetherAdd) {//������Ҫ��ӻ�ɾȥ���ļ��ľ���·��������ӵ����ļ��еľ���·����
        	 Boolean result=false;//�������ɹ�����true���ط������false  
        	 //�Ǹ�����true�������ӵ���false����ɾȥ
        	 Boolean whetherFloderFileExsit=false;//�ļ����ļ��������·�������Ƿ��Ǵ��ڵ�
        	 int floderFileDataNum=0;//��Ӧ�ļ��������±��
        	 //��Ѱ�Ҷ�Ӧ��floder�ļ�
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(flodername)) {//�ж���û�о���·������һ���Ĵ���
        			 floderFileDataNum=i;//��ֵ�±��
        			 whetherFloderFileExsit=true;
        			 break;
        		 }
        	 }
        	 if(whetherFloderFileExsit) {//��������ļ��в��б�Ҫ��������Ĳ���
        	     if(whetherAdd) {//���ӵ�
                      if(this.FileDataGroup.get(floderFileDataNum).getListNum()>0&&this.FileDataGroup.get(floderFileDataNum).getListNum()%8==0) {//�������ļ����������8�ļ���������Ϊ���õ��ܺ�ÿ�γ��������Ѿ��ڰ˵��ٽ�ֵ��    
        		          //����FAT
                    	  for(int i=0;i<256;i++) {
            		        if(this.FATformat.getDiskState()[i]==0) {
            			     result=true;
            			     int LastFatNum=this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().get(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-1);//��Ϊ��Ӷ��ǰ���������ǰԭ�������������������̿鱾������˳��ģ�ȡ���һ���Ŀ��
            			     this.FATformat.setDiskState(LastFatNum, i);//ԭ�����һ���ĩβָ���¼ӵĿ��
            			     this.FATformat.setDiskState(i,-1);//�����ȥFAT���Ӧ״̬�ֱ��-1
            			     this.FileDataGroup.get(floderFileDataNum).addUsingDiskNum(i);
            			     //�������ʽҲҪ�޸ĺ���ϢFAT����һ�����룬usingDiskNumҲҪ���� 
            			     String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
            			     //�ȴ����ֱ���ϼ���Ŀ¼�ĵ�
            			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//�ȸ���Щ����ϼ��ĵ�¼�������ļ�����
            			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], whetherAdd);
            			     }
            			     //����ֱ�ӵ��ϼ�Ŀ¼�ĵ�
            			     this.FileDataGroup.get(floderFileDataNum).addFileNameUnderFloder(fileName);
            			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()+1);
                    	    //��ȡroad��Ӧ�������ļ����ļ���Ȼ���������·�����Ƶ��ļ����ļ������FileNameUnderFloder��������ļ�������
                    	    //���ļ�����һ���ģ�ӦΪ��ֱ�ӹ������⴦��,listnumҲҪ��1
                    	     break; 
            		      }
        		        }
        		      }
                      else {//����Ĵ��̿�ռ仹���Ͳ���ҪFAT�����ˣ�ֻҪ�޸���FileGroup�ͺ�
                    	  result=true;
                    	  String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
         			     //�ȴ����ֱ���ϼ���Ŀ¼�ĵ�
         			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//�ȸ���Щ����ϼ��ĵ�¼�������ļ�����
         			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], whetherAdd);
         			     }
         			     //����ֱ�ӵ��ϼ�Ŀ¼�ĵ�
         			     this.FileDataGroup.get(floderFileDataNum).addFileNameUnderFloder(fileName);
         			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()+1);
                 	    //��ȡroad��Ӧ�������ļ����ļ���Ȼ���������·�����Ƶ��ļ����ļ������FileNameUnderFloder��������ļ�������
                 	    //���ļ�����һ���ģ�ӦΪ��ֱ�ӹ������⴦��,listnumҲҪ��1
                      }
                      
        	     }
             	 else {//ɾ����
        		 result=true;//��Ϊɾ�������ܿռ䲻��
        		    if(this.FileDataGroup.get(floderFileDataNum).getListNum()>8&&this.FileDataGroup.get(floderFileDataNum).getListNum()%8==1) {//��listnum����8�ҿ����ð�����������1�ʹ���ɾ�������ȥ��һ��ռ�õ�FAT�鵥Ԫ
        		    	int LastFatNum=this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().get(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-1);//��Ϊ��Ӷ��ǰ���������ǰԭ�������������������̿鱾������˳��ģ�ȡ���һ���Ŀ��
       			        int LastLastFatNum=this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().get(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-2);
        		    	this.FATformat.setDiskState(LastFatNum, 0);//ԭ�����һ���ĩβ�ָ�0���ճ�����
       			        this.FATformat.setDiskState(LastLastFatNum,-1);//FAT�����ڶ�������ļ���ʹ�õĿ��Ӧ״̬�ֱ��-1��Ϊ���һ��
       			        this.FileDataGroup.get(floderFileDataNum).indexDeleteUsingDiskNum(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-1);//ɾ��β��һ��
        		        //ɾ���ļ��ڸ��������ļ����������Ϣ
       			     String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
    			     //�ȴ����ֱ���ϼ���Ŀ¼�ĵ�
    			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//�ȸ���Щ����ϼ��ĵ�¼�������ļ�����
    			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], false);//false��ʾ��delete��true��add
    			     }
    			     //����ֱ�ӵ��ϼ�Ŀ¼�ĵ�
    			     this.FileDataGroup.get(floderFileDataNum).deleteFileNameUnderFloder(fileName);
    			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()-1);
    			   
        		    }
        		    else {//����Ͳ���Ҫ����FAT��usingdisknum��Щ����
        		    	result=true;
                  	  String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
       			     //�ȴ����ֱ���ϼ���Ŀ¼�ĵ�
       			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//�ȸ���Щ����ϼ��ĵ�¼�������ļ�����
       			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], false);//false��ʾ��delete��true��add
       			     }
       			     //����ֱ�ӵ��ϼ�Ŀ¼�ĵ�
       			     this.FileDataGroup.get(floderFileDataNum).deleteFileNameUnderFloder(fileName);
       			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()-1);
               	    //��ȡroad��Ӧ�������ļ����ļ���Ȼ���������·�����Ƶ��ļ����ļ������FileNameUnderFloderȥ������ļ�������
               	    //���ļ�����һ���ģ�ӦΪ��ֱ�ӹ������⴦��,listnumҲҪ��1
        		    }
        	     }
        	 }
        	 return result;
         }
       //���ӻ��߼����ļ������ļ��������ļ��������������ļ�ӵ��ļ���������(����ֱ����ӵ�һ������)��ֻ�Ǹı����ݣ������ļ�����filedata��ɾ����û�е�
         public Boolean addOrDeleteFileUnstraightToFloder(String fileName,String flodername,Boolean whetherAdd) {//������Ҫ��ӻ�ɾȥ���ļ��ľ���·��������ӵ����ļ��еľ���·����
        	 Boolean result=false;//�������ɹ�����true���ط������false  
        	 //�Ǹ�����true�������ӵ���false����ɾȥ
        	 Boolean whetherFloderFileExsit=false;//�ļ����ļ��������·�������Ƿ��Ǵ��ڵ�
        	 int floderFileDataNum=0;//��Ӧ�ļ��������±��
        	 //��Ѱ�Ҷ�Ӧ��floder�ļ�
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(flodername)) {//�ж���û�о���·������һ���Ĵ���
        			 floderFileDataNum=i;//��ֵ�±��
        			 whetherFloderFileExsit=true;
        			 break;
        		 }
        	 }
        	 if(whetherFloderFileExsit) {//��������ļ��в��б�Ҫ��������Ĳ���
        		 result=false;
        		 if(whetherAdd) {//���ӵ�����Ϊ�Ǽ�ӵ�����ֻ��Ҫ���ӵ�FileData�����������м���
        			 this.FileDataGroup.get(floderFileDataNum).addFileNameUnderFloder(fileName); 
        			 result=true;
        		 }
             	 else {//ɾ����
             		//������Ӧ���ļ����±�
             		this.FileDataGroup.get(floderFileDataNum).deleteFileNameUnderFloder(fileName); 
       			    result=true;	 
        	     }
        	 }
        	 return result;//��Ϊ��̬�������治����this�����޸ĺ��Ƿ������������
         } 
         //ɾ��ʱ�򶼼Ӹ��ж��Ҳ����ϼ�����������ֹ���������
       //ɾ��һ���ļ�,������¾��Ƿָ���һ����ϼ���һ�����ϼ��ļ��ж�������ļ����ļ�¼ɾ����Ȼ���ͷ�����ļ���FAT�������FileData��������ɾȥ����ļ�
       public Boolean deleteTxtOrExeKindFile(String fileAbsName) {//����������ļ��ľ���·�����ƣ�
    	    Boolean result=false;
    	    String allAboveFloderAbs[]=this.splitRoadToGetAllAboveFloder(fileAbsName);//��������ָ�����һ��Ԫ�ؾ��������������������ʵ�����������һ������Ч·�������ļ�����ľ���·���������ڶ�����·��ֱ�ӵ��ϼ��ļ��У������ļ����ü�Ӵ�������ļ��е�ɾ��Ҫ�����⴦��
    	    result=this.addOrDeleteFileStraightToFloder(fileAbsName,allAboveFloderAbs[allAboveFloderAbs.length-2],false);//ֱ�ӵ���������д��ֱ���ļ������ļ���ɾ������addOrDeleteFileStraightToFloder(String fileName,String flodername,Boolean whetherAdd) {//������Ҫ��ӻ�ɾȥ���ļ��ľ���·��������ӵ����ļ��еľ���·����
    	    int fileNum=0;//Ѱ���ļ���FileDataGroup�е��±�
    	    for(int i=0;i<this.FileDataGroup.size();i++) {
    	    	if(this.FileDataGroup.get(i).getFileName().equals(fileAbsName)&&(this.FileDataGroup.get(i).getFileKind()!=this.FileDataGroup.get(i).FLODER)) {
    	    		fileNum=i;
    	    		result=true;//�ļ��������ļ������ļ������͵ĺ�����������
    	    	}
    	    }
    	    if(result) {
    	    for(int i=0;i<this.FileDataGroup.get(fileNum).getUsingDiskNum().size();i++) {
    	    	this.FATformat.getDiskState()[this.FileDataGroup.get(fileNum).getUsingDiskNum().get(i)]=0;//�ָ�ûʹ��״̬
    	    }//�޸���ȫ��FAT״̬�ͷ����
    	    this.FileDataGroup.remove(fileNum);//ɾ������ļ���FileData
    	    //���������Ǹ�����û����FileData��ɾ���򴴽��ļ������Ի���Ҫɾ��һ������ļ��������������Ϣ�Լ�����ļ�����ռ�õ�FAT
    	    }
    	    return result;
       }
       //ɾ���ļ��������ļ� ����Ϊ�Ǹ��ݼ�¼��������ļ�һ������Ҫɾ����������Ϊɾ��ʱ����ܻ���ɾ���ϼ����ļ��У���ɾ��������ļ�ʱ��Ҫɾ����;������ļ��ж�������ļ�����Ϣ����ʱ����ܻ�����Ҳ����ϼ��ļ��е�������Ͳ��ܼ��������Ҿ����˷���ȫɾ�˾ͺ���
       public Boolean deleteFloderFile(String fileAbsName) {//����������ļ��ľ���·������
    	   Boolean result=false;
    	   String [] aboveFloderAbsRoadGroup=this.splitRoadToGetAllAboveFloder(fileAbsName);
    	   //��ȡ�˰����������·����ȫ������·���ַ���������������������һ���ַ���û��
    	   //
    	   //��������ˣ���д���ɾ���ĺ���ֻ��Ҫ�ṩ�ļ����ͺ��ˣ����ö�Ӧ�±�
//    	   int [] aboveFloderNumInFileDataGroup=new int [aboveFloderAbsRoadGroup.length-1];
//    	   //��¼������Щ�ϲ��ļ���filedata����������±�
//    	   
//    	   for(int i=0;i<aboveFloderAbsRoadGroup.length-1;i++) {
//    		   for(int j=0;j<this.FileDataGroup.size();j++) {
//    			   if(aboveFloderAbsRoadGroup[i].equals(this.FileDataGroup.get(j).getFileName())) {
//    				   aboveFloderNumInFileDataGroup[i]=j;
//    				   break;
//    			   }
//    		   }
//    	   }
    	   int thisFileNumInFileDataGroup=0;//Ҫɾ�����ļ��б�����FileDataGroup����������±�
    	   for(int j=0;j<this.FileDataGroup.size();j++) {
			   if(fileAbsName.equals(this.FileDataGroup.get(j).getFileName())&&(this.FileDataGroup.get(j).getFileKind()==this.FileDataGroup.get(j).FLODER)) {
				   thisFileNumInFileDataGroup=j;
				   result=true;//�ж�Ҫɾ�����ļ��Ƿ���ڣ����ļ�����Ҫ���ļ������ͣ�Ҫ���ļ��б������ھ�û��Ҫ����������
				   break;
			   }
		   }
    	   if(result) {//ֻ���ļ����������ļ��в�ִ�к���Ĳ���
    		   //��ɾ����ֱ����ϵ���ļ���Ҳ��Ҫɾ�����ļ���������ļ����������ļ�������
    		   for(int i=0;i<this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size();i++) {//��ѭ����Ҫɾ�����ļ���������ļ�����
    			   //��ѭ�����Ƕ�ӦҪɾ���ļ������ļ���¼����Щ�ϼ��ļ���
    			   for(int j=0;j<aboveFloderAbsRoadGroup.length-1;j++) {
    				   this.addOrDeleteFileUnstraightToFloder(this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().get(i),aboveFloderAbsRoadGroup[j],false);
    			       //ɾ��Ҫɾ�����ļ���������ļ����ļ����Ϸ��ļ���FileNameUnderFloder�еļ�¼��ӦΪ�Ǽ�����Ե��õ��Ǽ�ӷ������漰FAT�ı仯
    			   }
    		   }
    		   //���ɾ���ļ��ж�Ӧ��һ����ֱ����Ϣɾ������Ϊ��rootһ����ɾ����һ����root�¼��ļ������Լ�2�������
    		   this.addOrDeleteFileStraightToFloder(fileAbsName,aboveFloderAbsRoadGroup[aboveFloderAbsRoadGroup.length-2],false);
    	       //�������ͷ��ļ����Լ��ļ������������ļ���FATռ�õ�����
    		   //���ռ��ļ���������ļ���FileData�е��±�
    		   int [] underFloderNumInFileDataGroup=new int [this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size()];
               for(int i=0;i<this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size();i++) {
            	   for(int j=0;j<this.FileDataGroup.size();j++) {
            		   if(this.FileDataGroup.get(j).getFileName().equals(this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().get(i))) {
            			   underFloderNumInFileDataGroup[i]=j;
            			   break;
            		   }
            	   }
    		   }
    		   //���޸��ļ��������ļ���FAT�̿�״̬
    		   for(int i=0;i<underFloderNumInFileDataGroup.length;i++) {
    			   for(int j=0;j<this.FileDataGroup.get(underFloderNumInFileDataGroup[i]).getUsingDiskNum().size();j++) {
    				   this.FATformat.getDiskState()[this.FileDataGroup.get(underFloderNumInFileDataGroup[i]).getUsingDiskNum().get(j)]=0;
    			   }
    		   }
    		   //���޸ı��ļ��е�FAT�̿�״̬
    		   for(int j=0;j<this.FileDataGroup.get(thisFileNumInFileDataGroup).getUsingDiskNum().size();j++) {
				   this.FATformat.getDiskState()[this.FileDataGroup.get(thisFileNumInFileDataGroup).getUsingDiskNum().get(j)]=0;
			   }
    		   //�޸�FAT����Ϣ��Ϻ�ɾ���ļ����Լ��ļ���������ļ���FileDataGroup�е�Ԫ��
    		   //System.out.println("daxiao:"+underFloderNumInFileDataGroup.length);
//    		   for(int i=0;i<underFloderNumInFileDataGroup.length;i++) {//ɾ���ļ���������ļ�����
//    			   this.FileDataGroup.remove(underFloderNumInFileDataGroup[i]);
//    		   }
    		   for(int i=0;i<this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size();i++) {//ɾ���ļ���������ļ�����
    			   for(int j=0;j<this.FileDataGroup.size();j++) {
            		   if(this.FileDataGroup.get(j).getFileName().equals(this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().get(i))) {
            			   this.FileDataGroup.remove(j);
            			   break;
            		   }
            	   }
    		   }
    		   this.FileDataGroup.remove(thisFileNumInFileDataGroup);//ɾ���ļ��б���
    	   
    	   }
    	
    	   return result;
       }   
       //���Ʒ��ļ������͵��ļ���������Ҫ���Ƶ��ļ�����·�����Լ�Ҫ���Ƶ��ĵط��ľ���·��
       public Boolean copyTxtOrExeFile(String copyFileAbsName,String copyToWhereAbsRoad) {
    	   Boolean result=false;
    	   int beCopyedFileNumInFileDataGroup=0;
    	   for(int i=0;i<this.FileDataGroup.size();i++) {//�������������Ҫ���Ƶ��ļ�������ļ���Ӧ��FileDataGroup������±꣬��������ֱ�ӽ���
    	       if(this.FileDataGroup.get(i).getFileName().equals(copyFileAbsName)) {
    	    	   result=true;
    	    	   beCopyedFileNumInFileDataGroup=i;
    	    	   break;
    	       }
    	   }
    	   int avaliableDiskNum=0;//��û��ʹ�õĴ��̿���
    	   for(int i=0;i<256;i++){
    		   if(this.FATformat.getDiskState()[i]==0) {
    			   avaliableDiskNum++;
    		   }
    	   }//�õ���ûʹ�õĴ�������
    	   
    	   String [] splitResult=copyFileAbsName.split("\\\\");//�ָ��ļ���������
    	   String newCopyFileAbsName=copyToWhereAbsRoad+"\\"+splitResult[splitResult.length-1];//�����������
    	   for(int i=0;i<this.FileDataGroup.size();i++) {//�ж���û���Ѿ�����һģһ������·�����Ƶ��ļ��������result���false����Ͳ���Ҫ������
    		   if(this.FileDataGroup.get(i).getFileName().equals(newCopyFileAbsName)) {
    	    	   result=false;
    	    	   break;
    	       }
    	   }
    
    	   if(result&&this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getFileKind()==this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).TXT) {
    		   //�����TXT�ļ������ļ�����ʱ��
    		   result=false;//�������If�ͻ���true���򷵻�ʱ��������ִ��ʧ�ܵ�false
    		   if(this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getUsingDiskNum().size()<=avaliableDiskNum) {//������̹����ƲŻ�ִ�и���
    			   result=true;
    			   this.createTXTFile(copyToWhereAbsRoad,splitResult[splitResult.length-1]);
    			   this.changeTxtFileContext(this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getContext(),newCopyFileAbsName);
    		   }
    	   }
    	   if(result&&this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getFileKind()==this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).EXECUTABL) {
    		 //�����EXE�ļ������ļ�����ʱ��
    		   this.createEXEFile(copyToWhereAbsRoad,splitResult[splitResult.length-1]);
    	   }
    	   return result;
    	   //�ж����ı��ļ����ǿ�ִ���ļ�
    	   //��ȡ�ļ���Ҫռ�ݶ��ٴſ�
    	   //����FAT���洢�ռ��Ƿ񹻣�����ֱ�ӽ���
    	   //Ȼ��ֱ�����ļ��Ǿ������ʹ���·�����ô�����������һ���ļ�
    	   //������ı��ļ����ٸ���ԭ�ı��ļ�Context�������޸��ı��ļ����ı��ĺ����޸��½������ļ�
    	   /*�жϸ��Ƶ��ĵط��᲻������
    	    * �ļ���Ҫ���ƵĶ���������FileData
    	    * 1.�ļ������޸�·�����
    	    * 2.txt�Ļ�Ҫ����Context����
    	    * 3.FileKind
    	    * �ٸ����ļ���С����FAT
    	    * usingDiskNum
    	    */
       }
         
         //�ָ��ļ����ƣ����һ���·���ϵ��ļ��У��ڴ����ļ�ʱ���Ҫ�������ÿ���ļ��м���������ļ������֣�����ɾ��ʱ���ļ������������ļ�ɾ��
         public static String[] splitRoadToGetAllAboveFloder(String road) {//�������road�ǲ������ļ�����һ���road
        	 String [] splitResult=road.split("\\\\");//��Ϊ\���⣬�����ĸ��Ŵ�����б�ָܷ������ʱ��ָ�\˫б�ܲ���
        	 int aboveFloderNum=splitResult.length;
        	 String AllAboveFloder[]=new String[aboveFloderNum];
        	 for(int i=1;i<=aboveFloderNum;i++) {
        		 String result=splitResult[0];
        		 for(int j=1;j<i;j++) {
        			 result=result+"\\"+splitResult[j];
        		 }
        		 AllAboveFloder[i-1]=result;
        	 }
        	 return AllAboveFloder;//�Ӹ�Ŀ¼����˳��õ����ļ��о���·���ַ�������
         }
         //�޸�����ʱ����Ҫ�𿪸���������Ȼ���ٺ�������ɾ���·��������
         public static String changeName(String oldNameIncludeRoad,String name) {//�������ԭ���ľ���·�����ƺ��µ�����
        	 String [] splitResult=oldNameIncludeRoad.split("\\\\");//��Ϊ\���⣬�����ĸ��Ŵ�����б�ָܷ������ʱ��ָ�\˫б�ܲ���
        	 int Num=splitResult.length;
        	 String newNameIncludeRoad=splitResult[0];
        	 for(int i=1;i<Num-1;i++) {
        		 newNameIncludeRoad=newNameIncludeRoad+"\\"+splitResult[i];
        	 }
        	 newNameIncludeRoad=newNameIncludeRoad+"\\"+name;
        	 return newNameIncludeRoad;
         }
         
         
         
}
