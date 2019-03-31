CREATE TABLE `InventoryOrders` (
	`ReferenceNumber` int NOT NULL AUTO_INCREMENT,
	`OrderDate` DATE NOT NULL,
	`ArrivedDate` DATE NOT NULL,
	`SupplierId` DATE NOT NULL,
	PRIMARY KEY (`ReferenceNumber`)
);

CREATE TABLE `InventoryOrderDetails` (
	`ReferenceNumber` int NOT NULL,
	`ProductCode` int NOT NULL,
	`UnitPrice` bigint NOT NULL,
	`Units` bigint NOT NULL
);

ALTER TABLE `InventoryOrders` ADD CONSTRAINT `InventoryOrders_fk0` FOREIGN KEY (`SupplierId`) REFERENCES `Suppliers`(`SupplierID`);

ALTER TABLE `InventoryOrderDetails` ADD CONSTRAINT `InventoryOrderDetails_fk0` FOREIGN KEY (`ReferenceNumber`) REFERENCES `InventoryOrders`(`ReferenceNumber`);
