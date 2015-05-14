package com.vipulkanade.group12.cmpe272.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vipulkanade.group12.cmpe272.retailat12.R;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class PaymentActivity extends RetailAt12BaseActivity implements View.OnClickListener {

    private Button mScanCreditCardButton;
    private TextView mCreditNumberEditText;
    private TextView mExpirationDateEditText;

    private TextView mTextView;

    private int MY_SCAN_REQUEST_CODE = 100; // arbitrary int

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mTextView = (TextView) findViewById(R.id.textView5);
        mScanCreditCardButton = (Button) findViewById(R.id.credit_card_scan_button);
        mCreditNumberEditText = (TextView) findViewById(R.id.card_number);
        mExpirationDateEditText = (TextView) findViewById(R.id.expiration_date);

        mScanCreditCardButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CardIOActivity.canReadCardWithCamera()) {
            mTextView.setText("Scan a credit card with card.io");
        } else {
            mTextView.setText("Enter credit card information");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String resultStr;
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
            resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
            mCreditNumberEditText.setText(scanResult.getRedactedCardNumber());

            // Do something with the raw number, e.g.:
            // myService.setCardNumber( scanResult.cardNumber );

            if (scanResult.isExpiryValid()) {
                resultStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                mExpirationDateEditText.setText(scanResult.expiryMonth + "/" + scanResult.expiryYear);
            }

            if (scanResult.cvv != null) {
                // Never log or display a CVV
                resultStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
            }

            if (scanResult.postalCode != null) {
                resultStr += "Postal Code: " + scanResult.postalCode + "\n";
            }
        } else {
            resultStr = "Scan was canceled.";
        }
        Toast.makeText(getApplicationContext(), "Thank you for Shopping with us", Toast.LENGTH_SHORT).show();
        //resultTextView.setText(resultStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.credit_card_scan_button:
                Log.d("Scanned", "Not Scanned");
                Intent scanIntent = new Intent(this, CardIOActivity.class);

                // customize these values to suit your needs.
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

                // hides the manual entry button
                // if set, developers should provide their own manual entry mechanism in the app
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

                // matches the theme of your application
                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
                break;

            default:
                break;
        }
    }
}
