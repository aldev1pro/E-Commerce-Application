//package com.example.ee_shopping.`E-CommerceUi`
package com.example.ee_shopping.E

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailProductScreen(nav:(DetailProductUserEvent.NavigateToAnyScreen)->Unit) {


    val context = LocalContext.current.applicationContext
    val detailProduct: DetailProductViewModel =
        viewModel(factory = DetailProductFactory(context = context as Application))

    LaunchedEffect(key1 = true) {
        detailProduct.oneTimeDetail.collect { event ->
            when (event) {
                is DetailProductUserEvent.NavigateToAnyScreen -> {
                    nav(event)
                }
            }
        }
    }

    Column(
       // verticalArrangement = Arrangement.Center,
       // horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Card(
            modifier = Modifier.padding(5.dp),
            elevation = 10.dp,
            // shape = RoundedCornerShape(corner = CornerSize(10.dp)),

        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Back",
                        color = Color.Black, fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable {
                            detailProduct.executeDetail(DetailProductListEvents.OnNavigateBackToProductScreen)
                        }
                    )
                    Text(
                        text = "Cart",
                        color = Color.Red, fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable {
                            detailProduct.executeDetail(DetailProductListEvents.OnNavigateToAllItemsInCart)
                        }
                    )
                }
                Spacer(modifier = Modifier.size(15.dp))
                detailProduct.productImage?.let {
                    Image(
                        bitmap = it, contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .size(400.dp, 290.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.FillWidth
                    )

                }

                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = detailProduct.productName, fontWeight = FontWeight.ExtraBold)
                    Text(text = "D${detailProduct.basePrice}", fontWeight = FontWeight.ExtraBold,color = Color.Red)
                }
            }
        }
        Spacer(modifier = Modifier.size(30.dp))
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
        ) {
                val selected = remember { MutableInteractionSource() }
                val isPressed by selected.collectIsPressedAsState()
                val color = if (isPressed) Color.LightGray else Color.Red
                Spacer(modifier = Modifier.size(10.dp))
                Box(

                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {

                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(1))
                        }
                ) {
                    Text(text = "1")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(2))
                        }
                ) {
                    Text(text = "2")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(3))
                        }
                ) {
                    Text(text = "3")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(4))
                        }
                ) {
                    Text(text = "4")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(5))
                        }
                ) {
                    Text(text = "5")
                }

            }
        Spacer(modifier = Modifier.size(7.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.size(10.dp))

                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(6))
                        }
                ) {
                    Text(text = "6")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(7))
                        }
                ) {
                    Text(text = "7")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(8))
                        }
                ) {
                    Text(text = "8")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(9))
                        }
                ) {
                    Text(text = "9")
                }
                Spacer(modifier = Modifier.size(7.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(Color.LightGray)
                        .clickable {
                            detailProduct.executeDetail(DetailProductListEvents.SelectCartons(10))
                        }
                ) {
                    Text(text = "10")
                }
            }

            Spacer(modifier = Modifier.size(10.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "noOfCartons selected ${detailProduct.noOfItemsSelected}",
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                detailProduct.executeDetail(
                    DetailProductListEvents.AddProductToCart
                )
                Toast.makeText(context,
                    "${detailProduct.productName} added to cart",
                    Toast.LENGTH_SHORT).show()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier.fillMaxWidth().height(40.dp),

                ) {
                Text(text = "Add To Cart",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
            Spacer(modifier = Modifier.size(20.dp))

        }
    Spacer(modifier = Modifier.size(20.dp))


}