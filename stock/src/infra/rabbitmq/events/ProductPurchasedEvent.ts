export interface ProductPurchasedEvent {
	id: string
	providerId: string
	productId: string
	quantity: number
	price: number
	type: number
}
