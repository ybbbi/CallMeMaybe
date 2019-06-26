package com.ybbbi.googlestore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * ybbbi
 * 2019-06-25 14:10
 */
public class FlowView extends ViewGroup {
    private int horizontalSpacing = 15;//表示view之间的水平间距
    private int verticalSpacing = 15;//表示行之间的垂直间距

    /**
     * 设置水平间距的方法
     * @param horizontalSpacing
     */
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }
    /**
     * 设置垂直间距的方法
     * @param verticalSpacing
     */
    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    //用来记录所有的line对象
    private ArrayList<Line> lineList = new ArrayList<>();

    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 用来测量自己和自己的孩子
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //由于onMeasure方法可能执行多次，所以先清除一下lineList
        lineList.clear();

        //MeasureSpec:表示测量规则， 由2个因素构成：size和mode
        //size:具体的大小。比如100dp
        //mode:测量模式,其实就是对应的我们在布局中写的wrap_content,match_parent和具体的dp值
        // MeasureSpec.EXACTLY: 精确的，对应的是具体的dp，和match_parent
        // MeasureSpec.AT_MOST: 最多是多少，对应的是wrap_content
        // MeasureSpec.UNSPECIFIED: 未定义的，一般只在adapter的测量中用到
        //widthMeasureSpec是父View帮我们计算好并传入的, 我们可以反过来获取其中的size
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //计算实际用于比较的宽，就是总宽度减去左右padding
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();

        Line line = new Line();//准备一个line对象
        //开始遍历子View
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);//获取子View

            child.measure(0,0);

            //1.特殊情况：如果当前line中一个View都木有，那么不用比较了，则直接将
            //当前child放入line中，因为我们不能允许空行的存在
            if(line.viewList.size()==0){
                line.addLineView(child);
            }else if(line.lineWidth+child.getMeasuredWidth()+horizontalSpacing>noPaddingWidth){
                //2.比较当前行的宽+child的宽+水平间距是否大于noPaddingWidth，如果大于，需要将child放入下一行
                //否则，放入当前line中
                //先记录之前的line对象，然后再创建
                lineList.add(line);

                //创建新的line，来存放这个child
                line = new Line();
                line.addLineView(child);
            }else {
                //3.说明没有超过noPaddingWidth，那直接放入当前的line中
                line.addLineView(child);
            }

            //4.如果当前的child是最后一个子View，则需要手动的存储最后的line对象
            if(i==(getChildCount()-1)){
                lineList.add(line);//存储最后的一个line对象
            }
        }
        int height = getPaddingBottom()+getPaddingTop();
        //b.加上每个line的行高
        for (Line l : lineList){
            height += l.lineHeight;
        }
        //c.最后加上所有的行的垂直间距
        height += (lineList.size()-1)*verticalSpacing;

        //向父View申请宽高
        setMeasuredDimension(width,height);//自己来计算宽高
    }

    private boolean isClearRemainSpace = true;//是否清除掉留白

    public void setIsClearRemainSpace(boolean isClearRemainSpace){
        this.isClearRemainSpace = isClearRemainSpace;
    }

    /**
     * 摆放自己的孩子的位置
     * 取出lineList中的line，将每个line的VIew对象摆放到合适的位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);//获取line对象

            //从第二行开始，当前行的top比上一行多一个行高和垂直间距
            if(i>0){
                paddingTop += lineList.get(i-1).lineHeight+verticalSpacing;
            }

            int perSpace = 0;
            if(isClearRemainSpace){
                //a.获取留白的宽
                float remainSpace = getRemainSpace(line);
                //b.计算每个子View得到的值
                perSpace = (int) (remainSpace/line.viewList.size());
            }

            //遍历摆放line中的所有的View对象
            for (int j = 0; j < line.viewList.size(); j++) {
                View view = line.viewList.get(j);//取出View对象
                if(isClearRemainSpace){
                    //c.将得到的perSpace增加给view的左右padding
                    view.setPadding(view.getPaddingLeft()+perSpace/2,view.getPaddingTop(),
                            view.getPaddingRight()+perSpace/2,view.getPaddingBottom());
                    //重新测量
                    view.measure(0,0);
                }


                //摆放当前行的第1个
                if(j==0){
                    view.layout(paddingLeft,paddingTop,paddingLeft+view.getMeasuredWidth(),
                            paddingTop + view.getMeasuredHeight());
                }else {
                    //摆放后面的，需要参考前一个VIew的right
                    View preView = line.viewList.get(j - 1);
                    int left = preView.getRight()+horizontalSpacing;//前一个View的right+水平间距
                    view.layout(left,preView.getTop(),left+view.getMeasuredWidth(),preView.getBottom());
                }
            }
        }
    }

    /**
     * 获取行的留白距离
     * @param line
     * @return
     */
    private int getRemainSpace(Line line) {
        return   getMeasuredWidth() - getPaddingLeft()-getPaddingRight()-line.lineWidth;
    }

    /**
     * 行对象，用来封装每一行的数据
     */
    class Line {
        public ArrayList<View> viewList = new ArrayList<>();//用来存放当前行的View
        public int lineWidth;//用来记录当前行的宽，其实就是所有子View的宽+水平间距
        public int lineHeight;//当前行的高度，其实就是子View的高度

        public void addLineView(View view) {
            if (!viewList.contains(view)) {
                viewList.add(view);//将View对象添加到集合中

                //更新lineWidth
                if(viewList.size()==1){
                    //说明是第一个View，那么line的宽就是它的宽
                    lineWidth = view.getMeasuredWidth();
                }else {
                    //说明不是第一个，应该在line宽度的基础上+view的宽+水平间距
                    lineWidth += view.getMeasuredWidth() + horizontalSpacing;
                }

                //更新lineHeight
                lineHeight = view.getMeasuredHeight();
            }
        }
    }

}
