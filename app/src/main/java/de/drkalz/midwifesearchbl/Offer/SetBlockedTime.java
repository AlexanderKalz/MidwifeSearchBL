package de.drkalz.midwifesearchbl.offer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.drkalz.midwifesearchbl.MainActivity;
import de.drkalz.midwifesearchbl.R;
import de.drkalz.midwifesearchbl.StartApp;
import de.drkalz.midwifesearchbl.dataObjects.BlockedTime;

public class SetBlockedTime extends AppCompatActivity {

    final StartApp sApp = StartApp.getInstance();
    final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    ArrayList<String> savedBlock = new ArrayList<>();
    List<BlockedTime> savedList = new ArrayList<>();
    TextView headline;
    ArrayAdapter arrayAdapter;
    ListView showBlockedDates;
    EditText startDate, stopDate;
    Button addBlock;
    ImageButton saveButton, endActivity;
    Date startOfBlock, endOfBlock;
    boolean addItem;
    int positionOfItem;

    private void createDate() {
        try {
            startOfBlock = sdf.parse(startDate.getText().toString());
            endOfBlock = sdf.parse(stopDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveItem(View view) {

        createDate();
        if (addItem) {
            updateDatabase(1);
        } else {
            updateDatabase(2);
        }
        startDate.setVisibility(View.INVISIBLE);
        startDate.setText("");
        stopDate.setVisibility(View.INVISIBLE);
        stopDate.setText("");
        addBlock.setVisibility(View.VISIBLE);
        headline.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        arrayAdapter.notifyDataSetChanged();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void updateDatabase(int toDo) {

        BlockedTime newBlock = new BlockedTime();
        newBlock.setStartOfBlock(startOfBlock);
        newBlock.setEndOfBlock(endOfBlock);
        newBlock.setMidwifeID(sApp.getCurrentUser().getObjectId());
        savedList.add(newBlock);
        BackendlessUser saveMidwifePortfolio = sApp.getCurrentUser();
        saveMidwifePortfolio.setProperty("setBlockedTime", savedList);

        switch (toDo) {
            // Create item
            case 1:
                Backendless.UserService.update(saveMidwifePortfolio, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(getApplicationContext(), "Block wurde gespeichert", Toast.LENGTH_LONG).show();
                        savedBlock.add("Start: " + sdf.format(startOfBlock) + " - Ende: " + sdf.format(endOfBlock));
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), "Block wurde NICHT gespeichert!\n" + fault.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;

            // update item
            case 2:
                newBlock.setObjectId(savedList.get(positionOfItem).getObjectId());
                Backendless.Persistence.save(newBlock, new AsyncCallback<BlockedTime>() {
                    @Override
                    public void handleResponse(BlockedTime response) {
                        Toast.makeText(getApplicationContext(), "Block wurde aktualisiert", Toast.LENGTH_LONG).show();
                        savedBlock.set(positionOfItem, "Start: " + sdf.format(startOfBlock) + " - Ende: " + sdf.format(endOfBlock));
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), "Block wurde NICHT aktualisiert!\n" + fault.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            // delete item
            case 3:
                newBlock.setObjectId(savedList.get(positionOfItem).getObjectId());
                Backendless.Persistence.of(BlockedTime.class).remove(newBlock, new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        Toast.makeText(getApplicationContext(), "Block wurde gelöscht", Toast.LENGTH_LONG).show();
                        savedBlock.remove(positionOfItem);
                        savedList.remove(positionOfItem);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), "Block wurde NICHT gelöscht!\n" + fault.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_blocked_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headline = (TextView) findViewById(R.id.tv_headline);
        showBlockedDates = (ListView) findViewById(R.id.lv_BlockedDates);
        startDate = (EditText) findViewById(R.id.et_start);
        stopDate = (EditText) findViewById(R.id.et_stop);
        addBlock = (Button) findViewById(R.id.bu_addTime);
        saveButton = (ImageButton) findViewById(R.id.ib_Save);
        saveButton.setVisibility(View.INVISIBLE);
        endActivity = (ImageButton) findViewById(R.id.ib_goBack);

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, savedBlock);
        showBlockedDates.setAdapter(arrayAdapter);

        String whereClause = "Users[setBlockedTime].objectId='" + sApp.getCurrentUser().getObjectId() + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        Backendless.Persistence.of(BlockedTime.class).find(dataQuery, new AsyncCallback<BackendlessCollection<BlockedTime>>() {
            @Override
            public void handleResponse(BackendlessCollection<BlockedTime> response) {
                if (response.getTotalObjects() > 0) {
                    for (BlockedTime item : response.getData()) {
                        savedBlock.add("Start: " + sdf.format(item.getStartOfBlock()) + " - Ende: " + sdf.format(item.getEndOfBlock()));
                        savedList.add(item);
                    }
                } else {
                    savedBlock.clear();
                    savedList.clear();
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });

        arrayAdapter.notifyDataSetChanged();

        showBlockedDates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Backendless.Persistence.of(BlockedTime.class).findById(savedList.get(position).getObjectId(), new AsyncCallback<BlockedTime>() {
                    @Override
                    public void handleResponse(BlockedTime response) {
                        startOfBlock = response.getStartOfBlock();
                        endOfBlock = response.getEndOfBlock();
                        startDate.setText(sdf.format(startOfBlock));
                        stopDate.setText(sdf.format(endOfBlock));
                        headline.setVisibility(View.INVISIBLE);
                        startDate.setVisibility(View.VISIBLE);
                        stopDate.setVisibility(View.VISIBLE);
                        addBlock.setVisibility(View.INVISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                        addItem = false;
                        positionOfItem = position;
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }
        });

        showBlockedDates.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positionOfItem = position;
                updateDatabase(3);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        addBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headline.setVisibility(View.INVISIBLE);
                startDate.setVisibility(View.VISIBLE);
                stopDate.setVisibility(View.VISIBLE);
                addBlock.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                addItem = true;
            }
        });

        endActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
