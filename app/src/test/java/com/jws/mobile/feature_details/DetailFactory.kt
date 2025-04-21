package com.jws.mobile.feature_details

import com.jws.mobile.core.features.Picture
import com.jws.mobile.feature_detail.domain.Detail

object DetailFactory {
    fun getData(
        id: String = "MLA123",
        title: String = "Producto de prueba",
        price: Int = 1000,
        basePrice: Int = 1200,
        availableQuantity: Int = 10,
        initialQuantity: Int = 20,
        condition: String = "new",
        pictures: List<Picture> = listOf(Picture(id = "1" ,url = "http://example.com/img1.jpg")),
        warranty: String = "Garantia de 6 meses",
        acceptsMercadopago: Boolean = true,
        buyingMode: String = "buy_it_now",
        internationalDeliveryMode: String = "none"
    ): Detail {
        return Detail(
            id = id,
            title = title,
            price = price,
            basePrice = basePrice,
            availableQuantity = availableQuantity,
            initialQuantity = initialQuantity,
            condition = condition,
            pictures = pictures,
            warranty = warranty,
            acceptsMercadopago = acceptsMercadopago,
            buyingMode = buyingMode,
            internationalDeliveryMode = internationalDeliveryMode
        )
    }
}