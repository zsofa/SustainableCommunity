package Progmatic.SustainableCommunity.dataloader;

import Progmatic.SustainableCommunity.jpaRepos.ItemRepo;
import Progmatic.SustainableCommunity.models.Item;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class DataLoaderController {

    ItemRepo itemRepo;


    @PostMapping(
            path = "itemImg/upload/{itemId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadImage(@PathVariable("itemId") Long itemId,
                                       @RequestParam("image") MultipartFile file) throws IOException {
        Item item = itemRepo.getById(itemId);
        byte[] image = file.getBytes();
        item.setItemImage(image);
        itemRepo.save(item);
    }

    @GetMapping(path = "itemImg/download/{itemId}",
                produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public byte[] downloadItemImageFromDB(@PathVariable("itemId") Long itemId) {
        Item item = itemRepo.getById(itemId);

        return item.getItemImage();
    }

    @GetMapping("items")
    public List<Item> getItems() {
        return itemRepo.findAll();
    }
}
