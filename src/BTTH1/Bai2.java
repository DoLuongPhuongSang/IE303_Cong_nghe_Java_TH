/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BTTH1;

import java.util.Random;

public class Bai2 {
    public static double xapXiPi(int soMau) {
        Random ngauNhien = new Random();
        int diemNamTrongHinhTron = 0;

        for (int i = 0; i < soMau; i++) {
            double x = ngauNhien.nextDouble()*2-1; // X ngau nhien trong [-1,1]
            double y = ngauNhien.nextDouble()*2-1; // Y ngau nhien trong [-1,1]
            
            if (x * x + y * y <= 1) {
                diemNamTrongHinhTron++;
            }
        }
        
        // Uoc luong pi bang phuong phap hinh tron 1/4
        return 4.0 * diemNamTrongHinhTron / soMau;
    }

    public static void main(String[] args) {
        int soMau = 1000000; // So diem ngau nhien de uoc luong
        double piUocLuong = xapXiPi(soMau);
        System.out.println("Gia tri uoc luong cua pi la: " + piUocLuong);
    }
}
