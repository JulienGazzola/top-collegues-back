package dev.top.entities;

public class CollegueApi extends Collegue {

	public void setPhoto(String photo) {
		this.setImageUrl(photo);
	}

	public String getPhoto() {
		return this.getImageUrl();
	}
}