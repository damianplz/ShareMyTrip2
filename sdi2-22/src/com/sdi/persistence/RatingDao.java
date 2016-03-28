package com.sdi.persistence;

import com.sdi.model.Rating;
import com.sdi.persistence.util.GenericDao;


public interface RatingDao extends GenericDao<Rating, Long> {

	Rating findByAboutFrom(
			Long aboutUserId, 
			Long aboutTripId, 
			Long fromUserId, 
			Long fromTripId
		); 
	
}
