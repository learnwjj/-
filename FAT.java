package Disk;

import java.io.Serializable;

/*
 * FAT��Ҳ�����̿�ĵ�һ�����ݣ����ڵ�һ��̶���FAT����ֱ�ӵ����ó�����Ϊһ����洢
 * �ܹ�256���̣���0��1�ſ��ڴ洢����ʾʱ��ֱ��Ĭ��0��1�Ѿ����䣬Ȼ�������FAT��¼�仯����
 */
public class FAT implements Serializable{
     private int Disknum[];//256���̿��
     private int DiskState[];//256���̵�״̬��¼��Ҳ�������û�з������0.��������ʾ���ӵ���һ���̵ĺ���������������޾ͼ�¼-1
     //Ϊ�󷽱���������256��С������������Ӧ��¼���ڵ��ļ��Ŀ�ͷ�̿���Լ��ļ������ļ���������ַ
     /*
      * 
      */
     private int DiskheadGroup[];//��¼�����ļ��洢�ĵ�һ���̿��
     private String FileName[];//��¼��Ӧ���ļ���������·��������
     /*
      * ����׸�����ں���û��ʹ��
      */
     
     public FAT() {//��ʼ����һ��ʱ��֮��Ӧ�þ��Ƕ�ȡ���������Ķ��󼴿�
    	 this.Disknum=new int [256];
    	 this.DiskState=new int [256];
    	 for(int i=0;i<256;i++) {
    		 this.Disknum[i]=i;//���̿������
    		 this.DiskState[i]=0;//�����̿���δ����״̬
    	 }
    	 this.DiskState[0]=-1;
    	 this.DiskState[1]=-1; //�̿�0��1FAT��Ȼ��ʹ��״̬
    	 this.DiskState[2]=-1; //�̿�2�Ǹ���Ŀ¼�õ�
    	 
    	 
    	 this.DiskheadGroup=new int [256];//-1��ʾû�з���
    	 this.FileName=new String [256];
    	 for(int i=0;i<256;i++) {
    		 this.DiskheadGroup[i]=-1;//���̿������
    		 this.FileName[i]="";//�����̿���δ����״̬
    	 }
    	 this.DiskheadGroup[0]=0;
    	 this.DiskheadGroup[1]=1;
    	 this.DiskheadGroup[2]=2;
    	 this.FileName[0]="FAT1";
    	 this.FileName[1]="FAT2";
    	 this.FileName[3]="ROOT";//��Ŀ¼�ļ�������
    			 
     }
     
     //�ж��Ƿ������̿���Ƿ����
     public Boolean judgeIfFATAllocation(int requestNum) {//����������ռ�õİ���
    	 Boolean result=true;//�������ռ�õ��̿��Ѿ�ռ�þͷ���false
    	 if(this.DiskState[requestNum]!=0)
    		 result=false;
    	 return result;
     }
     
     //����ſ�

     
	public int[] getDisknum() {
		return Disknum;
	}
//	public void setDisknum(int[] disknum) {
//		Disknum = disknum;
//	}�������޸��̿��
	public int[] getDiskState() {
		return DiskState;
	}
	public void setDiskState(int num,int state) {//��һ�������Ǹı��diskState���±꣬�ڶ����Ǹı���ֵ
		this.DiskState[num]=state;
	}
     
}
