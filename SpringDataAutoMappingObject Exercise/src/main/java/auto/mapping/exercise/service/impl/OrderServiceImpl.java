package auto.mapping.exercise.service.impl;

import auto.mapping.exercise.repository.OrderRepository;
import auto.mapping.exercise.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public void addItem(String gameTitle) {

    }
}
