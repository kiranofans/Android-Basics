package Helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppSignatureHelper extends ContextWrapper {
    /** Getting APK Hash code for SMS construction
     * Debug and Release APKs have different hash code, so make sure
     * getting hash code form Release build*/
    private static final String TAG = AppSignatureHelper.class.getSimpleName();

    private static final String HASH_TYPE = "SHA-256";
    public static final int NUM_HASHED_BYTES = 9;
    public static final int NUM_BASE64_CHAR = 11;

    public AppSignatureHelper(Context base) {
        super(base);
    }

    /**
     * Get all the app signatures for the current package
     */
    public List<String> getAppSignature(){
        List<String> appCodeList = new ArrayList<>();

        try{
            //Get all package signatures for the current package
            String packageName = getPackageName();
            PackageManager packMgr = getPackageManager();
            Signature[] signatures = packMgr.getPackageInfo
                    (packageName,PackageManager.GET_SIGNATURES).signatures;
            //GET_SIGNATURES is deprecated

            //Iterate each signature, create a compatible hash
            for(Signature signature:signatures){
                String hash = hash(packageName, signature.toCharsString());
                if(hash != null){
                    appCodeList.add(String.format("%s",hash));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG,"Unable to find package to obtain hash. ",e);
        }
        return appCodeList;
    }

    private static String hash(String packageName, String signatrue){
        String appInfo = packageName + " "+ signatrue;
        try{
            MessageDigest msgDigest = MessageDigest.getInstance(HASH_TYPE);
            msgDigest.update(appInfo.getBytes(StandardCharsets.UTF_8));
            byte[] hashSignature = msgDigest.digest();

            //Truncated into NUM_HASHED_BYTES
            hashSignature = Arrays.copyOfRange(hashSignature,0,NUM_HASHED_BYTES);

            //Encode into Base64
            String base64Hash = Base64.encodeToString(hashSignature,
                    Base64.NO_PADDING | Base64.NO_WRAP);
            base64Hash = base64Hash.substring(0,NUM_BASE64_CHAR);

            Log.d(TAG,String.format("pkg: %s -- hash: %s",packageName,base64Hash));
            return base64Hash;
        }catch (NoSuchAlgorithmException e){
            Log.e(TAG, "hash:NoSuchAlgorithm", e);
        }
        return null;
    }
}
