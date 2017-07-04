package us.poe2017.service;

import us.poe2017.data.entity.*;
import us.poe2017.data.repository.GroupRepository;
import us.poe2017.data.repository.GroupResponseRepository;
import us.poe2017.data.repository.GuestRepository;
import us.poe2017.dto.*;
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
    GroupResponseRepository groupResponseRepository;


    public Group findGroupByCode(String code) {
        return groupRepository.findOne(code.toUpperCase())
                .map(RsvpService::entityToDtoConverter)
                .orElseThrow(() -> new RuntimeException("Secret code not found!"));
    }

    public static Group entityToDtoConverter(GroupEntity groupEntity){
        return new Group(groupEntity.getCode(), groupEntity.getGroupName(), entityToDtoConverter(groupEntity.getGuests()));
    }

    private static List<Guest> entityToDtoConverter(List<GuestEntity> guests) {
        return guests.stream().map(guest -> new Guest(guest.getId(), guest.getName(), guest.isPlusOne())).collect(Collectors.toList());
    }

    public void saveResponse(Response response) {
        GroupEntity group = groupRepository.findOne(response.getCode()).orElseThrow(() -> new RuntimeException("Invalid code, no group found!"));
        GroupResponseEntity groupResponseEntity = new GroupResponseEntity(group, response.getEmail(), response.getDietaryRestrictions(), response.getComments());
        for (GuestEntity guest : group.getGuests()){
            if (response.getGuestResponses().containsKey(guest.getId())) {
                GuestResponse guestResponse = response.getGuestResponses().get(guest.getId());
                GuestResponseEntity guestResponseEntity = new GuestResponseEntity(groupResponseEntity, guest, guestResponse.getAttending(), guestResponse.getPlusOneName());
                groupResponseEntity.getGuests().add((guestResponseEntity));
            }
            else {
                throw new RuntimeException("Didn't recieve a response from guest " + guest.getName());
            }
        }
        groupResponseRepository.save(groupResponseEntity);
    }
}
