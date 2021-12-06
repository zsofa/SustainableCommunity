package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.DTOs.ItemDTO;
import Progmatic.SustainableCommunity.jpaRepos.ItemRepo;
import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.Item;
import Progmatic.SustainableCommunity.storage.BucketName;
import Progmatic.SustainableCommunity.storage.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class ItemService {
    
    private ItemRepo itemRepo;
    private final FileStore fileStore;

    @Autowired
    public ItemService(ItemRepo itemRepo, FileStore fileStore) {
        this.itemRepo = itemRepo;
        this.fileStore = fileStore;
    }

    @Transactional
    public void uploadItem(ItemDTO item, AppUser appUser) { // bemeneti par ItemFrom *
        Item saveItem = new Item(item);
        appUser.getUploadItems().add(saveItem);
        itemRepo.save(saveItem);
    }

    public void uploadItemWImage(ItemDTO item, AppUser user, MultipartFile file) {
        Item saveItem = new Item(item);
        itemRepo.save(saveItem);
        // 1. check if image is not empty
        boolean imgIsEmpty = file.isEmpty();
        if(imgIsEmpty) {
            throw new IllegalStateException("file is empty");
        }
        // 2. If file is an image
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("file is not an image");
        }

        // 4. Grab some metadata if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        // 5. store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBUCKET_NAME(),saveItem.getItemId());

        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path,filename, Optional.of(metadata),file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }



        user.getUploadItems().add(saveItem);

        saveItem.setImgLink(filename);

        try {
            saveItem.setItemImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemRepo.save(saveItem);

    }


    public byte[] downloadItemImgAWS(Long itemId) {

        Item item = itemRepo.getById(itemId);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBUCKET_NAME(),item.getItemId());

        return item.getImgLink()
                .map(key -> fileStore.download(path,key))
                .orElse(new byte[0]);
    }

    /**
     * Egy adott tárgy kölcsönzésekor mennyit lehet vele spórolni.
     * 1 napra vonatkozóan.
     */
    public Integer moneySaved(Item item) {
        return item.getItemValue() - item.getBorrowPrice();
    }

    /**
     * Egy adott tárgy kölcsönzésekor mekkora helyet lehet vele spórolni.
     * cm-ben megadva, metódus átválja méterre, hogy nm-ert kapjunk.
     */

    public Double spaceSaved(Item item) {
        return item.getItemHeight() * item.getItemWidth() / 100;
    }

    /**
     *
     * changeItemPrice (Item)
     * Admin jogosultság, kikeresi az Itemet id alapján, átállítja az isApprovalt.
     */

    private void approveItem(Item toBeApproved, Integer price) {
        Optional<Item> op = itemRepo.findById(toBeApproved.getItemId());
        if (op.isPresent()) {
            Item toApproved = op.get();
            toApproved.setBorrowPrice(price);
            toApproved.setIsApproved(true);
            itemRepo.save(toApproved);
        }
    }

    /**
     * Visszaadja az Admin számára a még nem approvalt itemeket.
     */

    private Optional<List<Item>> approveItemList() {
        return itemRepo.findAllByIsApprovedFalse();
    }

    /**
     * Kiszámolja az Item ratingjét.
     * @param rating
     * @param item
     * @return
     */
    public Double getItemRate(Double rating, Item item){
        Double rate = item.getRatings() + rating;
        item.setRatings(rate);
        item.setRateCounter(item.getRateCounter()+1);
        item.setItemRating(item.getRatings()/item.getRateCounter());
        return item.getItemRating();
    }

    public Item reserveItem(Item item) {
        item.setIsAvailable(false);
        return item;
    }

    public AppUser addToRentedItemList(Item item, AppUser appUser) {
        appUser.getRentedItems().add(item);
        return appUser;
    }

    public Item enableItem(Item item) {
        item.setIsAvailable(true);
        itemRepo.save(item);
        return item;
    }

}

