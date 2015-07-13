package bugs;

/**
 * Created by tunyash on 7/11/15.
 */
public enum Direction {
    UP{
        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getDc() {
            return 0;
        }

        @Override
        public int getDr() {
            return -1;
        }
    }, RIGHT{
        @Override
        public int getNumber() {
            return 1;
        }
        @Override
        public int getDc() {
            return 1;
        }

        @Override
        public int getDr() {
            return 0;
        }
    }, DOWN{
        @Override
        public int getNumber() {
            return 2;
        }
        @Override
        public int getDc() {
            return 0;
        }

        @Override
        public int getDr() {
            return 1;
        }
    }, LEFT{
        @Override
        public int getNumber() {
            return 3;
        }
        @Override
        public int getDc() {
            return -1;
        }

        @Override
        public int getDr() {
            return 0;
        }
    };
    public abstract int getNumber();

    /**
     *
     * @return change of row number moving current direction
     */
    public abstract int getDr();

    /**
     *
     * @return change of column number moving current direction
     */
    public abstract int getDc();



}
