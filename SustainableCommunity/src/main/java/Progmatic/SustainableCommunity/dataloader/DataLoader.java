package Progmatic.SustainableCommunity.dataloader;

import Progmatic.SustainableCommunity.jpaRepos.ItemRepo;
import Progmatic.SustainableCommunity.jpaRepos.UserRepo;
import Progmatic.SustainableCommunity.models.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DataLoader {

    private final UserRepo userRepo;
    private final ItemRepo itemRepo;

    @Bean
    public CommandLineRunner testData() {
        AppUser admin = new AppUser(
                "admin",
                "admin@nemkacat.com",
                "password",
                UserRole.ADMIN
        );

        AppUser customer = new AppUser(
                "customer",
                "customer@nemkacat.com",
                "password"
        );

        AppUser seller = new AppUser(
                "seller",
                "seller@nemkacat.com",
                "password"
        );


        AppUser owner = userRepo.getById(3L);
        AppUser charterer = userRepo.getById(2L);

        Item test1 = new Item(
                "Fűrész",
                ItemCategory.ADVENTURING,
                ItemCondition.GOOD,
                120000,
                8000,
                "Zsófa nagyon jó fűrésze.",
                20.0,
                80.0,
                null,
                true,
                true,
                ItemStatus.APPROVED,
                owner,
                null,
                1,
                5.0,
                5.0,
                5.0

        );

        Item rented = new Item(
                "varrógép",
                ItemCategory.DIY,
                ItemCondition.FAIR,
                90000,
                5000,
                "Nagyi nagyon jó varrógépe.",
                40.0,
                60.0,
                null,
                false,
                true,
                ItemStatus.APPROVED,
                owner,
                charterer,
                3,
                9.0,
                3.0,
                10.0

        );

        Item newItem = new Item(
                "pálinkafőző",
                ItemCategory.DIY,
                ItemCondition.POOR,
                50000,
                4000,
                "Döbrögi nagyon jó pálinkafőzője.",
                80.0,
                83.0,
                null,
                false,
                false,
                ItemStatus.PENDING,
                charterer,
                null,
                0,
                0.0,
                0.0,
                60.0

        );



        return args -> {
                /*userRepo.save(admin);
                userRepo.save(customer);
                userRepo.save(seller);
                itemRepo.save(test1);
                itemRepo.save(rented);
                itemRepo.save(newItem);*/
        };
    }
}
