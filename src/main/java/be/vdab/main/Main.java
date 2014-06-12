package be.vdab.main;

import java.util.Scanner;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		System.out.print("User name:");
		String userName = scanner.nextLine();
		System.out.print("Password:");
		String origineelPaswoord = scanner.nextLine();
		System.out.print("Encrypted password:");
		System.out.println(encoder.encodePassword(origineelPaswoord, userName));
		scanner.close();
	}
}
