package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		List<Product> products = new ArrayList<>();

		try {
			System.out.println("Entre com o caminho do arquivo: ");
			String path = input.nextLine();

			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				String line = br.readLine();
				while (line != null) {
					String[] values = line.split(",");
					products.add(new Product(values[0], Double.parseDouble(values[1])));
					line = br.readLine();
				}
				// Price averege
				double avg = products.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / products.size();
				System.out.println("Preço médio: R$ " + String.format("%.2f", avg));
				// Ordered names
				Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
				List<String> names = products.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName())
						.sorted(comp.reversed()).collect(Collectors.toList());
				names.forEach(System.out::println);

			} catch (IOException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		} catch (InputMismatchException e) {
			System.out.println("\nErro de formato. Foi inserido um valor inadequado para operação.");
			System.out.println("Por favor, reinicie o programa e tenta novamente.");
		} finally {
			if (input != null)
				input.close();
		}
	}
}
