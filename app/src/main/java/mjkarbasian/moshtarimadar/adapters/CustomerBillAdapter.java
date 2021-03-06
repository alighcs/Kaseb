package mjkarbasian.moshtarimadar.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 7/30/2016.
 */
public class CustomerBillAdapter extends CursorAdapter {
    Context mContext;
    private LayoutInflater cursorInflater;
    String saleId;
    String[] mProjection;
    String saleCode;
    String dueDate;
    String totalDue;
    Cursor mCursor;

    public CustomerBillAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_item_bills, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewDate = (TextView) view.findViewById(R.id.item_list_bill_due_date);
        TextView textViewPurchaseAmount = (TextView) view.findViewById(R.id.item_list_bill_purchase_amount);
        TextView textViewSaleCode = (TextView) view.findViewById(R.id.item_list_bill_sale_code);

        saleId = cursor.getString(cursor.getColumnIndex(KasebContract.Sales._ID));
        saleCode = cursor.getString(cursor.getColumnIndex(KasebContract.Sales.COLUMN_SALE_CODE));

        mProjection = new String[]{
                KasebContract.DetailSale._ID,
                KasebContract.DetailSale.COLUMN_DATE,
                KasebContract.DetailSale.COLUMN_TOTAL_DUE};

        mCursor = context.getContentResolver().query(
                KasebContract.DetailSale.saleDetailSale(Long.parseLong(saleId)),
                mProjection,
                null,
                null,
                null);

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                dueDate = mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_DATE));
                totalDue = mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
            }
        }

        mCursor.close();

        textViewDate.setText(dueDate);
        textViewPurchaseAmount.setText(totalDue);
        textViewSaleCode.setText(saleCode);
    }
}
