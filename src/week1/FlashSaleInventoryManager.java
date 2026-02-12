package week1;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FlashSaleInventoryManager {

    private HashMap<String, AtomicInteger> stockMap;
    private HashMap<String, Queue<Integer>> waitingListMap;

    public FlashSaleInventoryManager() {
        stockMap = new HashMap<>();
        waitingListMap = new HashMap<>();
    }

    public void addProduct(String productId, int stock) {
        stockMap.put(productId, new AtomicInteger(stock));
        waitingListMap.put(productId, new LinkedList<>());
    }

    public String checkStock(String productId) {
        if (!stockMap.containsKey(productId)) {
            return "Product not found";
        }

        int stock = stockMap.get(productId).get();
        return stock + " units available";
    }

    public synchronized String purchaseItem(String productId, int userId) {

        if (!stockMap.containsKey(productId)) {
            return "Product not found";
        }

        AtomicInteger stock = stockMap.get(productId);

        if (stock.get() > 0) {
            int remaining = stock.decrementAndGet();
            return "Success, " + remaining + " units remaining";
        }

        Queue<Integer> queue = waitingListMap.get(productId);
        queue.offer(userId);

        return "Added to waiting list, position #" + queue.size();
    }

    public synchronized String processWaitingList(String productId) {

        if (!stockMap.containsKey(productId)) {
            return "Product not found";
        }

        Queue<Integer> queue = waitingListMap.get(productId);

        if (queue.isEmpty()) {
            return "No waiting customers";
        }

        int userId = queue.poll();
        return "User " + userId + " notified for availability";
    }

    public static void main(String[] args) {

        FlashSaleInventoryManager manager = new FlashSaleInventoryManager();

        manager.addProduct("IPHONE15_256GB", 3);

        System.out.println("checkStock → " + manager.checkStock("IPHONE15_256GB"));

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 11111));

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99999));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 88888));

        System.out.println(manager.processWaitingList("IPHONE15_256GB"));
    }
}
