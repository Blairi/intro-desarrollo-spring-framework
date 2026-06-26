package mx.com.unam.sispro3d.service;

import mx.com.unam.sispro3d.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SISPRO3DService {

    @Autowired
    private Category category;

    @Autowired
    private Account account;

    @Autowired
    private ServiceOffering service;

    @Autowired
    private Review review;

    public void mostrarCategory() {
        System.out.println("=== CATEGORY ===");
        System.out.println("ID: " + category.getId());
        System.out.println("Nombre: " + category.getName());
        System.out.println("Descripción: " + category.getDescription());
    }

    public void mostrarAccount() {
        System.out.println("=== ACCOUNT ===");
        System.out.println("ID: " + account.getIdUser());
        System.out.println("Nombre: " + account.getName() + " " + account.getLastName());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Tipo: " + account.getType());
    }

    public void mostrarService() {
        System.out.println("=== SERVICE ===");
        System.out.println("ID: " + service.getId());
        System.out.println("Título: " + service.getTitle());
        System.out.println("Precio base: $" + service.getBasePrice());
        System.out.println("Categoría: " + service.getCategory().getName());
    }

    public void mostrarReview() {
        System.out.println("=== REVIEW ===");
        System.out.println("ID: " + review.getId());
        System.out.println("Rating: " + review.getRating() + "/5");
        System.out.println("Comentario: " + review.getComment());
        System.out.println("Cliente: " + review.getAccount().getName());
        System.out.println("Servicio: " + review.getService().getTitle());
    }
}