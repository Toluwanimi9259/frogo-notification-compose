package com.techafresh.frogonotificationcompose

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.techafresh.frogonotificationcompose.reply.NotificationService
import com.techafresh.frogonotificationcompose.ui.theme.FrogoNotificationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrogoNotificationComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val ctx = LocalContext.current
                        Button(onClick = {
                            val intent = Intent(this@MainActivity , MainActivity::class.java)
                            val pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                            sendNormalNotification(
                                ctx,
                                "Channel 01",
                                "Normal Channel",
                                "This is the title",
                                "Content of the message",
                                "Sub Text",
                                NotificationManager.IMPORTANCE_HIGH,
                                intent = pendingIntent
                            )
                        }) {
                            Text(text = "Normal Notification")
                        }

                        Button(onClick = {
                            val intent = Intent(this@MainActivity , MainActivity::class.java)
                            val pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                            sendCustomNotification(
                                ctx,
                                "Channel 02",
                                "Custom Channel",
                                importance = NotificationManager.IMPORTANCE_HIGH,
                                intent = pendingIntent
                            )
                        }) {
                            Text(text = "Custom Layout")
                        }

                        Button(onClick = {
                            sendStack(
                                context = ctx,
                                sender = generateRandomData().split(',')[1],
                                message = generateRandomData().split(',')[0]
                            )

                        }) {
                            Text(text = "Stack Notification")
                        }

                        Button(onClick = {
                            startService(Intent(this@MainActivity, NotificationService::class.java))
                        }) {
                            Text(text = "Reply Notification")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FrogoNotificationComposeTheme {

    }
}