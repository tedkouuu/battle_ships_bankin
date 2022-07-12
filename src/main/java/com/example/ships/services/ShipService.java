package com.example.ships.services;

import com.example.ships.models.Category;
import com.example.ships.models.CategoryEnum;
import com.example.ships.models.Ship;
import com.example.ships.models.User;
import com.example.ships.models.dto.CreateShipDTO;
import com.example.ships.models.dto.ShipDTO;
import com.example.ships.repositories.CategoryRepository;
import com.example.ships.repositories.ShipRepository;
import com.example.ships.repositories.UserRepository;
import com.example.ships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    public ShipService(ShipRepository shipRepository, CategoryRepository categoryRepository,
                       UserRepository userRepository, LoggedUser loggedUser) {

        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }


    public boolean create(CreateShipDTO createShipDTO) {

        Optional<Ship> findShip = this.shipRepository.findShipByName(createShipDTO.getName());

        if (findShip.isPresent()) {
            return false;
        }

        CategoryEnum type = switch (createShipDTO.getCategory()) {
            case 0 -> CategoryEnum.BATTLE;
            case 1 -> CategoryEnum.CARGO;
            case 2 -> CategoryEnum.PATROL;
            default -> CategoryEnum.BATTLE;
        };

        Optional<Category> category = this.categoryRepository.findCategoryByName(type);

        Optional<User> owner = this.userRepository.findById(loggedUser.getId());


        Ship ship = new Ship().setName(createShipDTO.getName())
                .setCreated(createShipDTO.getCreated()).setHealth(createShipDTO.getHealth())
                .setPower(createShipDTO.getPower()).setCategory(category.get()).setUser(owner.get());


        this.shipRepository.save(ship);

        return true;

    }

    public List<ShipDTO> getOwnedShips(Long id) {

        List<ShipDTO> ownedShips = new ArrayList<>();

        List<Ship> ownShips = this.shipRepository.findByUserId(id);

        for (Ship ownShip : ownShips) {

            ShipDTO shipDTO = new ShipDTO().setName(ownShip.getName())
                    .setId(ownShip.getId()).setPower(ownShip.getPower())
                    .setHealth(ownShip.getHealth());

            ownedShips.add(shipDTO);
        }

        return ownedShips;


    }

    public List<ShipDTO> getEnemyShips(Long id) {

        List<ShipDTO> enemyShips = new ArrayList<>();

        List<Ship> ownShips = this.shipRepository.findByUserIdNot(id);

        for (Ship ownShip : ownShips) {

            ShipDTO shipDTO = new ShipDTO().setName(ownShip.getName())
                    .setId(ownShip.getId()).setPower(ownShip.getPower())
                    .setHealth(ownShip.getHealth());

            enemyShips.add(shipDTO);
        }

        return enemyShips;


    }

    public List<ShipDTO> getAllShipsSorted() {

        List<ShipDTO> sortedShips = new ArrayList<>();

        List<Ship> sorted = this.shipRepository.findByOrderByHealthAscNameAscPowerAsc();

        for (Ship ship : sorted) {

            ShipDTO shipDTO = new ShipDTO().setName(ship.getName())
                    .setId(ship.getId()).setPower(ship.getPower())
                    .setHealth(ship.getHealth());

            sortedShips.add(shipDTO);
        }

        return sortedShips;

    }
}













