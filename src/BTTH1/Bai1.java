/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BTTH1;

/**
 *
 * @author pc
 */
import java.util.Random;

public class Bai1 {
    public static double uocLuongDienTich(double banKinh, int soMau) {
        Random ngauNhien = new Random();
        int diemNamTrongHinhTron = 0;

        for (int i = 0; i < soMau; i++) {
            double x = (ngauNhien.nextDouble() * 2 - 1) * banKinh; // X ngau nhien trong [-banKinh, banKinh]
            double y = (ngauNhien.nextDouble() * 2 - 1) * banKinh; // Y ngau nhien trong [-banKinh, banKinh]
            
            if (x * x + y * y <= banKinh * banKinh) {
                diemNamTrongHinhTron++;
            }
        }
        
        // Uoc luong so pi bang (4 * diem trong hinh tron / tong so mau)
        double piUocLuong = 4.0 * diemNamTrongHinhTron / soMau;
        return piUocLuong * banKinh * banKinh; // Dien tich uoc luong S = pi * r^2
    }

    public static void main(String[] args) {
        double banKinh = 5.0; // Vi du ban kinh
        int soMau = 1000000; // So diem ngau nhien de xap xi
        
        double dienTich = uocLuongDienTich(banKinh, soMau);
        System.out.println("Dien tich xap xi cua hinh tron co ban kinh " + banKinh + " la: " + dienTich);
    }
}
