package com.example.parasrawat2124.tictactoe.ModelClass;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

public class MyDragEventListener implements View.OnDragListener {


    @Override
    public boolean onDrag(View v, DragEvent event) {

        final int action = event.getAction();
        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do,
                    // applies a blue color tint to the View to indicate that it can accept
                    // data.
                    v.setBackgroundColor(Color.BLUE);

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                v.setBackgroundColor(Color.GREEN);
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                v.setBackgroundColor(Color.GREEN);

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();

                return true;

            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                // Turns off any color tints
                v.setBackgroundColor(Color.WHITE);

                // Invalidates the view to force a redraw
                v.invalidate();

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                // Turns off any color tinting


                // Invalidates the view to force a redraw
                v.invalidate();

                if (event.getResult()) {


                } else {


                }
                return true;
                default:
                    Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                    break;

        }
        return false;
    }
}

