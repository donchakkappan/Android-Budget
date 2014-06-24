package com.kites.budget;

public class GridViewItemModel {

	
	
	private  String name="";
	private  String image=""; 

	/*********** Set Methods ******************/
	public void setName(String name)
	{
		this.name =name;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}
	
	
	/*********** Get Methods ****************/
	public String getName()
	{
		return this.name;
	}
	
	public String getImage()
	{
		return this.image;
	}

}
