

*** remote / local services ***

1. Remote: 52.34.169.54 with port 3000, AWS, MySQL database
	- 6 tables: User, Product, BuyerProduct, SellerProduct, Group, Images
	- urls used for.. 
		1) sign in
		2) sign up
		3) get group (logo + domain)
		4) send verification email
		5) leave group
		6) get all products (for particular group)
		7) fetch all images (encoded in base64)
		8) upload product
		9) update buyer product relationship + priority for buy! button 
		10) insert microphone recorded data
		11) fetch order page (bought + selling) 
		
2. Local:
	- 2 tables: Product, Images
	(holding onto heavy ones only, which take lot of time when querying from
	remote overtime)








