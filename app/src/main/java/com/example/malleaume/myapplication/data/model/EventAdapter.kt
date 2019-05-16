import android.widget.TextView
import android.widget.ArrayAdapter


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import android.view.*
import android.widget.ImageView
import com.example.malleaume.myapplication.R
import com.example.malleaume.myapplication.data.model.Event

import android.os.Bundle
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.startActivity
import com.example.malleaume.myapplication.Ui.EventDetails
import kotlinx.android.synthetic.main.item_event.view.*


class EventAdapter(context: Context, Event: ArrayList<Event>) : ArrayAdapter<Event>(context, 0, Event) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        // Get the data item for this position
        val event = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }else{

        }
        // Lookup view for data population
        val TvTitreEvent = convertView!!.findViewById(R.id.TvTitreEvent) as TextView
        val IvFond = convertView!!.findViewById(R.id.IvFond) as ImageView



        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            IvFond.setImageResource(R.drawable.street_art)
        }
        else
        {
            // Set the background color for alternate row/item
            convertView.setBackgroundColor(Color.parseColor("#EEECEC"));
        }


        // Populate the data into the template view using the data object
        TvTitreEvent.text = event!!.LIBELLE


        convertView.BtnDetails.setOnClickListener{



            val intent = Intent(context, EventDetails()::class.java)
            intent.putExtra(EventDetails.EVENT, event)
            startActivity(context, intent, Bundle())
        }

        /*val imgBytesData = android.util.Base64.decode(EventDetails!!.picture, 1)
        val bitmap = BitmapFactory.decodeByteArray(imgBytesData, 0, imgBytesData.size)

        IvEvent.setImageBitmap(bitmap)*/

        // Return the completed view to render on screen
        return convertView


    }



}



