package com.supreme.smartclusters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.supreme.smartclusters.marks.AppliedStep;
import com.supreme.smartclusters.marks.EngStep;
import com.supreme.smartclusters.marks.GradeStep;
import com.supreme.smartclusters.marks.HumStep;
import com.supreme.smartclusters.marks.KiswStep;
import com.supreme.smartclusters.marks.MathStep;
import com.supreme.smartclusters.marks.SciAStep;
import com.supreme.smartclusters.marks.SciBStep;

import java.util.HashMap;
import java.util.Map;

import ernestoyaquello.com.verticalstepperform.Step;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;


public class MarksEntry extends Activity implements StepperFormListener {

    private EngStep engStep;
    private KiswStep kiswStep;
    private MathStep mathStep;
    private SciAStep sciAStep;
    private SciBStep sciBStep;
    private HumStep humStep;
    private AppliedStep appliedStep;
    private GradeStep gradeStep;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;


    private VerticalStepperFormView verticalStepperForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marksentry);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        // Create the steps.
        engStep = new EngStep("English Score");
        kiswStep=new KiswStep("Kiswahili Score");
        mathStep=new MathStep("Math Score");
        sciAStep=new SciAStep("Highest science score");
        sciBStep=new SciBStep("2nd highest Science score");
        humStep=new HumStep("Humanities score");
        appliedStep=new AppliedStep("Applied score");
        gradeStep=new GradeStep("Overal Grade");



        // Find the form view, set it up and initialize it.
        verticalStepperForm = findViewById(R.id.stepper_form);
        verticalStepperForm
                .setup(this, engStep,kiswStep,mathStep,sciAStep,sciBStep,humStep,appliedStep,gradeStep)
                .init();
    }

    @Override
    public void onCompletedForm() {
        // This method will be called when the user clicks on the last confirmation button of the
        // form in an attempt to save or send the data.

        String eng = engStep.getStepData();
        String kisw = kiswStep.getStepData();
        String math = mathStep.getStepData();
        String sciA = sciAStep.getStepData();
        String sciB = sciBStep.getStepData();
        String hum = humStep.getStepData();
        String applied = appliedStep.getStepData();
        String grade = gradeStep.getStepData();
        int english=Integer.parseInt(eng);
        int kiswa=Integer.parseInt(kisw);
        int maths=Integer.parseInt(math);
        int scia=Integer.parseInt(sciA);
        int scib=Integer.parseInt(sciB);
        int human=Integer.parseInt(hum);
        int app=Integer.parseInt(applied);
        int t=english+kiswa+maths+scia+scib+human+app;
        int T=84;
        int a=maths;
        int b=Math.max(kiswa,english);
        int c=Math.max(scia,scib);
        int d=Math.max(human,app);
        int r=a+b+c+d;
        int R=48;
        double f=Math.sqrt((r/R)*(t/T));
        f= (int) (f*48);

        Map<String, Object> post = new HashMap<>();


        post.put("eng", eng);
        post.put("kisw", kisw);
        post.put("math", math);
        post.put("sciA", sciA);
        post.put("sciB", sciB);
        post.put("hum",hum);
        post.put("applied", applied);
        post.put("total",t);
        post.put("grade",grade);
        post.put("cluster",f);





        FirebaseFirestore.getInstance().collection("marksdata").document(userId)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // pdialog.dismiss();
                        Toast.makeText(MarksEntry.this, "upload success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                        finish();


                    }
                });
    }


    @Override
    public void onCancelledForm() {
        // This method will be called when the user clicks on the cancel button of the form.
    }


    public void onStepAdded(int index, Step<?> addedStep) {
        // This will be called when a step is added dynamically through the form method addStep().
    }

    @Override
    public void onStepRemoved(int index) {
        // This will be called when a step is removed dynamically through the form method removeStep().
    }
}