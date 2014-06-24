package com.kites.budget;

public class ListItemModel {

	
	
	private  String envelopeName="";
	private  String purpose=""; 
	private  String upordown=""; 
	private  String upordownImage=""; 
	private  String amount=""; 
	private  String date=""; 
	

	/*********** Set Methods ******************/
	
	public void setEnvelopename(String envelopeName)
	{
		this.envelopeName =envelopeName;
	}
	public void setPurpose(String purpose)
	{
		this.purpose=purpose;
	}
	public void setUporDownLabel(String upordown)
	{
		this.upordown=upordown;
	}
	public void setImage(String upordownImage)
	{
		this.upordownImage=upordownImage;
	}
	public void setAmount(String amount)
	{
		this.amount=amount;
	}
	public void setDate(String date)
	{
		this.date=date;
	}
		
	
	/*********** Get Methods ****************/
	public String getEnvelopeName()
	{
		return this.envelopeName;
	}
	
	public String getPurpose()
	{
		return this.purpose;
	}
	public String getUporDownLabel()
	{
		return this.upordown;
	}
	
	public String getupordownImage()
	{
		return this.upordownImage;
	}
	public String getAmount()
	{
		return this.amount;
	}
	public String getDate()
	{
		return this.date;
	}

}
