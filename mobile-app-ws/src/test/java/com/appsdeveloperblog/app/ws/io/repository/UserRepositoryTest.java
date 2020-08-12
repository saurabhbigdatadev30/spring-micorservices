package com.appsdeveloperblog.app.ws.io.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest

class UserRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Autowired
	UserRepository userRepository;
	
	static boolean recordsCreated = false;
	

	@BeforeEach
	void setUp() throws Exception {
		
		if(!recordsCreated) createRecrods();
	}

	@Test
	final void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 1);
		LOGGER.debug(">>>>>>>>><<<<<<<<<<<<<<<< testGetVerifiedUsers +++++++++++++++++");
		//Pageable pageableRequest = PageRequest.of(page, limit);
		/*page - page No 
		 *limit - No of records to be displayed in the page
		 *
		 * i.e 
		 * PageRequest.of(0, 1); 
		 * on pg = 0 , 1 record will be displayed
		 * 
		 */
		Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
		assertNotNull(page);
        List<UserEntity> userEntities = page.getContent();
        for (UserEntity userEntitity : userEntities) {
        System.out.println(userEntitity.getFirstName());	
        }
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 1);
	}
	
	@Test 
	final void testFindUserByFirstName()
	{
		String firstName="Sergey";
		List<UserEntity> users = userRepository.findUserByFirstName(firstName);
		assertNotNull(users);
		assertTrue(users.size() == 2);
		
		UserEntity user = users.get(0);
		assertTrue(user.getFirstName().equals(firstName));
	}
	
	@Test 
	final void testFindUserByLastName()
	{
		String lastName="Kargopolov";
		List<UserEntity> users = userRepository.findUserByLastName(lastName);
		assertNotNull(users);
		assertTrue(users.size() == 2);
		
		UserEntity user = users.get(0);
		assertTrue(user.getLastName().equals(lastName));
	}
	
	@Test 
	final void testFindUsersByKeyword()
	{
		String keyword="erg";
		List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
		assertNotNull(users);
		assertTrue(users.size() == 2);
		
		UserEntity user = users.get(0);
		assertTrue(
				user.getLastName().contains(keyword) ||
				user.getFirstName().contains(keyword)
				);
	}
	
	@Test 
	final void testFindUserFirstNameAndLastNameByKeyword()
	{
		String keyword="erg";
		List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
		assertNotNull(users);
		assertTrue(users.size() == 2);
		Object[] user = users.get(0);
		assertTrue(user.length == 2);
		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);
		assertNotNull(userFirstName);
		assertNotNull(userLastName);
		
		System.out.println("First name = " + userFirstName);
		System.out.println("Last name = " + userLastName);
		
	}
 
	@Test 
	final void testUpdateUserEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "1a2b3c");
		
		UserEntity storedUserDetails = userRepository.findByUserId("1a2b3c");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

	}
	
	
	@Test 
	final void testFindUserEntityByUserId()
	{
		String userId = "1a2b3c";
		UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
	}
	
	@Test
	final void testGetUserEntityFullNameById()
	{
		String userId = "1a2b3c";
		List<Object[]> records =  userRepository.getUserEntityFullNameById(userId);
		
        assertNotNull(records);
        assertTrue(records.size() == 1);
        
        Object[] userDetails = records.get(0);
      
        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
	}
	
	@Test 
	final void testUpdateUserEntityEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "1a2b3c");
		
		UserEntity storedUserDetails = userRepository.findByUserId("1a2b3c");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

	}
	
	private void createRecrods()
	{
		// Prepare User Entity
	     UserEntity userEntity = new UserEntity();
	     userEntity.setFirstName("Sergey");
	     userEntity.setLastName("Kargopolov");
	     userEntity.setUserId("1a2b3c");
	     userEntity.setEncryptedPassword("xxx");
	     userEntity.setEmail("test@test.com");
	     userEntity.setEmailVerificationStatus(true);
	     
	     // Prepare User Addresses
	     AddressEntity addressEntity = new AddressEntity();
	     addressEntity.setType("shipping");
	     addressEntity.setAddressId("Office");
	     addressEntity.setCity("Pune");
	     addressEntity.setCountry("India ");
	     addressEntity.setPostalCode("Viman");
	     addressEntity.setStreetName("TRION  Address");
	     
	     
	     AddressEntity addressEntity2user1 = new AddressEntity();
	     addressEntity2user1.setType("Home");
	     addressEntity2user1.setAddressId("111");
	     addressEntity2user1.setCity("PCMC");
	     addressEntity2user1.setCountry("INDIA");
	     addressEntity2user1.setPostalCode("WAKAD");
	     addressEntity2user1.setStreetName("PS");
	     List<AddressEntity> addresseslst = new ArrayList<>();
	     addresseslst.add(addressEntity);
	     addresseslst.add(addressEntity2user1);
	     userEntity.setAddresses(addresseslst);
	     
	     
	     for(int i=0;i<userEntity.getAddresses().size();i++)
			{
	    	    AddressEntity address = userEntity.getAddresses().get(i);
	    	    // bidirectional relationship
				address.setUserDetails(userEntity);
				userEntity.getAddresses().set(i, address);
			}
	     
	     
	     
	     System.out.println(userEntity.getEmailVerificationStatus());
	     userRepository.save(userEntity);    
			/*
			 * // Prepare User Entity UserEntity userEntity2 = new UserEntity();
			 * userEntity2.setFirstName("Sergey2"); userEntity2.setLastName("Kargopolov2");
			 * userEntity2.setUserId("1a2b3cddddd2");
			 * userEntity2.setEncryptedPassword("xxx2");
			 * userEntity2.setEmail("test2@test.com");
			 * userEntity2.setEmailVerificationStatus(true);
			 * 
			 * // Prepare User Addresses AddressEntity addressEntity2 = new AddressEntity();
			 * addressEntity2.setType("shipping2");
			 * addressEntity2.setAddressId("ahgyt74hfywwww2");
			 * addressEntity2.setCity("Vancouver2"); addressEntity2.setCountry("Canada2");
			 * addressEntity2.setPostalCode("ABCCDA2");
			 * addressEntity2.setStreetName("123 Street Address");
			 * 
			 * List<AddressEntity> addresses2 = new ArrayList<>();
			 * addresses2.add(addressEntity2); userEntity2.setAddresses(addresses2);
			 * 
			 * userRepository.save(userEntity2);
			 */
	     recordsCreated = true;
    
	}

}
