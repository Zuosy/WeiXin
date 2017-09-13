package com.misaka.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
    
    private static String  token="misaka";
    
    /**
     * �������������Լ�΢�ŵ�token����̬�Լ��趨����֤��
     * @param signature ǩ��������ʵ���Ľ���Ƿ�һ��        
     * @param timestamp ʱ����
     * @param nonce ������ֱ��
     * @return һ������ֵȷ�������ܵõ����Ƿ���signatureһ��
     */
    public static boolean checkSignature(String signature,
            String timestamp,String nonce){
        //������������һ��String����Ȼ������ֵ�����
        String[] arr=new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        //����һ�����󴢴����������String�Ľ����
        StringBuilder content=new StringBuilder();
        for(int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }
        
        
        //����sha1���ܷ��Ĺ���
        MessageDigest md=null;
        String tmpStr=null;
        try {
            md=MessageDigest.getInstance("SHA-1");
            //md.digest()���������������ֽ�����
            byte[] digest=md.digest(content.toString().getBytes());
            //���ֽ�����Ū���ַ���
            tmpStr=byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        content=null;
        
        return tmpStr!=null?tmpStr.equals(signature.toUpperCase()):false;
        
    }
    
    
    /**
     * ���ֽڼӹ�Ȼ��ת�����ַ���
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest){
        String strDigest="";
        for(int i=0;i<digest.length;i++){
            //��ȡ���ַ��Ķ�������ת��Ϊ16������ĵ������ַ���
            strDigest+=byteToHexStr(digest[i]);
        }
        return strDigest;
    }
    
    /**
     * ��ÿ���ֽڼӹ���һ��16λ���ַ���
     * @param b
     * @return
     */
    public static String byteToHexStr(byte b){
        //תλ�����ձ�
        char[] Digit= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
        
        //λ������2����ת��Ϊ16����
        char[] tempArr=new char[2];
        tempArr[0]=Digit[(b>>>4)&0X0F];//XXXX&1111��ô�õ��Ļ���XXXX
        tempArr[1]=Digit[b&0X0F];//XXXX&1111��ô�õ��Ļ���XXXX
        
        //�õ���������ַ���
        String s=new String(tempArr);
        return s;
    }
}
