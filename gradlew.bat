<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    
    <androidx.cardview.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="10dp"
        android:layout_marginTop="6dp"
        android:layout_marginRight="10dp"
        android:layout_marginBottom="6dp"
        app:cardCornerRadius="10dp"
        app:cardElevation="@dimen/cardview_default_elevation">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <ImageView
                android:id="@+id/img"
                android:transitionName="img"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:scaleType="center"/>

            <ImageView
                android:id="@+id/shadowBottom"
                android:layout_width="match_parent"
                android:layout_height="80dp"
                android:src="@drawable/bottom_shadow"
                android:layout_alignBottom="@+id/img"/>
            
            <ProgressBar
                android:id="@+id/loadImageProgressBar"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
            

        </RelativeLayout>

    </androidx.cardview.widget.CardView>

</FrameLayout>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           