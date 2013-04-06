package com.neodem.upcConverter.bean;


public class CD {
	private String upcCode;
	private String artist;
	private String albumTitle;
	private String rating;
	
	/**
	 * @return the upcCode
	 */
	public String getUpcCode() {
		return upcCode;
	}
	
	/**
	 * @param upcCode the upcCode to set
	 */
	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}
	
	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}
	
	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	/**
	 * @return the albumTitle
	 */
	public String getAlbumTitle() {
		return albumTitle;
	}
	
	/**
	 * @param albumTitle the albumTitle to set
	 */
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	
	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}
	
	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
}
