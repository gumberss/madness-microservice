import { PurchaseType } from '../enums/PurchaseType'

export class ProductService {
	public productUnitQuantityByType(quantity: number, type: PurchaseType) {
		switch (type) {
			case PurchaseType.UNIT:
				return quantity
			case PurchaseType.BOX:
				return quantity * 4
			case PurchaseType.PALLET:
				return quantity * 12
		}
	}
}
