package us.poe2017.service;

import us.poe2017.data.entity.GroupEntity;
import us.poe2017.data.entity.GroupResponseEntity;
import us.poe2017.data.entity.GuestEntity;
import us.poe2017.data.entity.GuestResponseEntity;
import us.poe2017.data.repository.GroupRepository;
import us.poe2017.data.repository.GroupResponseRepository;
import us.poe2017.data.repository.GuestRepository;
import us.poe2017.dto.Group;
import us.poe2017.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.poe2017.dto.Group;
import us.poe2017.dto.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jdifebo on 3/14/2017.
 */
@Service
public class RsvpService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    GroupResponseRepository groupResponseRepository;

    public Group findGroupByCode(String code) {
        return groupRepository.findOne(code)
                .map(RsvpService::entityToDtoConverter)
                .orElseThrow(RuntimeException::new);
    }

    public static Group entityToDtoConverter(GroupEntity groupEntity){
        return new Group(groupEntity.getCode(), groupEntity.getGroupName(), entityToDtoConverter(groupEntity.getGuests()));
    }

    private static List<String> entityToDtoConverter(List<GuestEntity> guests) {
        return guests.stream().map(guest -> guest.getName()).collect(Collectors.toList());
    }

    public void saveResponse(Response response) {
        GroupEntity group = groupRepository.findOne(response.getCode()).orElseThrow(() -> new RuntimeException("Invalid code, no group found!"));
        GroupResponseEntity groupResponseEntity = new GroupResponseEntity(group, response.getEmail(), response.getDietaryRestrictions(), response.getComments());
        for (String guestName : response.getAttending().keySet()){
            GuestEntity guest = guestRepository.findOne(guestName).orElseThrow(() -> new RuntimeException("Couldn't find that guest!"));
            GuestResponseEntity guestResponseEntity = new GuestResponseEntity(groupResponseEntity, guest, response.getAttending().get(guestName));
            groupResponseEntity.getGuests().add((guestResponseEntity));
        }
        groupResponseRepository.save(groupResponseEntity);
    }
}
